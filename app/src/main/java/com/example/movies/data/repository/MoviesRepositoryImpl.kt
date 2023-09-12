package com.example.movies.data.repository

import android.util.Log
import com.example.movies.data.db.MoviesDao
import com.example.movies.data.network.MoviesService
import com.example.movies.data.network.model.MOVIE_IMAGE_BASE_URL_400
import com.example.movies.domain.model.DetailsMovie
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.MovieReviews
import com.example.movies.domain.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepositoryImpl @Inject constructor(
    private val moviesDao: MoviesDao, private val moviesService: MoviesService
) : MoviesRepository {

    override val movies: Flow<List<Movie>> = moviesDao.getMovies()

    override suspend fun getMovies(currentMovies: Int) {
        withContext(Dispatchers.IO) {
            try {
                val nextPage = (currentMovies / 20) + 1
                Log.d("juacoCompose", "inserting in db, page: $nextPage")
                val newMovies = moviesService.getMovies(page = nextPage).results.map { it.toLocalModel() }
                Log.d("juacoCompose", "inserting in db, last movie from backend: ${newMovies.last().title}")
                val insertResult = moviesDao.insertMovies(newMovies)
                Log.d("juacoCompose", "inserting in db, insertResult: $insertResult")
                val moviesInDB = moviesDao.getMoviesAsync()
                Log.d("juacoCompose", "inserted in db, is last api movie in db: ${
                    moviesInDB.indexOfFirst { it.id == newMovies.last().id } 
                }"
                )
            } catch (e: Exception) {
                Log.d(
                    "juacoCompose", "inserting in db, error while inserting"
                )
            }
            val moviesInDB = moviesDao.getMoviesAsync()
            Log.d("juacoCompose", "inserted in db, moviesSize = ${moviesInDB.size}")
            Log.d("juacoCompose", "inserted in db, lastMovie = ${moviesInDB.last().title}")
        }
    }

    override suspend fun getMovieDetails(movieId: Int): DetailsMovie {
        return moviesService.getMovieDetails(movieId).toModel()
    }

    override suspend fun deleteExpiredMovies() {
        withContext(Dispatchers.IO) {
            val minTimeStamp = System.currentTimeMillis() - TIME_MOVIE_AVAILABLE
            moviesDao.deleteExpiredMovies(deleteTimeMin = minTimeStamp)
        }
    }

    override suspend fun getMovieReviews(movieId: Int, page: Int): MovieReviews {
        val response = moviesService.getMovieReviews(movieId)
        return MovieReviews(
            amountOfReviews = response.totalResults, reviews = response.results.map {
                val avatarPath = it.authorDetails?.avatarPath
                it.copy(authorDetails = it.authorDetails?.copy(avatarPath = MOVIE_IMAGE_BASE_URL_400 + avatarPath))
            }, totalPages = response.totalPages
        )
    }

}

const val TIME_MOVIE_AVAILABLE = 24 * 60 * 60 * 1000