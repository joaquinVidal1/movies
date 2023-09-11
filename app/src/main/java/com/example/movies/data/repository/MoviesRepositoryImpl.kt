package com.example.movies.data.repository

import androidx.lifecycle.LiveData
import com.example.movies.data.db.MoviesDao
import com.example.movies.data.network.MoviesService
import com.example.movies.domain.model.DetailsMovie
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.MovieReview
import com.example.movies.domain.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepositoryImpl @Inject constructor(
    private val moviesDao: MoviesDao, private val moviesService: MoviesService
) : MoviesRepository {

    override val movies: LiveData<List<Movie>> = moviesDao.getMovies()

    override suspend fun getMovies(page: Int) {
        withContext(Dispatchers.IO) {
            val newMovies = moviesService.getMovies(page = page).results.map { it.toLocalModel() }
            moviesDao.insertMovies(newMovies)
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

    override suspend fun getMovieReviews(movieId: Int, page: Int): List<MovieReview> {
        return moviesService.getMovieReviews(movieId).results
    }

}

const val TIME_MOVIE_AVAILABLE = 24 * 60 * 60 * 1000