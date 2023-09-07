package com.example.movies.repository

import androidx.lifecycle.LiveData
import androidx.room.Transaction
import com.example.movies.db.MoviesDao
import com.example.movies.model.Movie
import com.example.movies.network.MoviesService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepositoryImpl @Inject constructor(
    private val moviesDao: MoviesDao, private val moviesService: MoviesService
) : MoviesRepository {

    override val movies: LiveData<List<Movie>> = moviesDao.getMovies()

    @Transaction
    override suspend fun updateMovies() {
        withContext(Dispatchers.IO) {
            val minTimeStamp = System.currentTimeMillis() - TIME_MOVIE_AVAILABLE
            moviesDao.deleteExpiredMovies(deleteTimeMin = minTimeStamp)
            val newMovies = moviesService.getMovies(page = 1).results.map { it.toLocalModel() }
            moviesDao.insertMovies(newMovies)
        }
    }

    override suspend fun getMoreMovies(page: Int) {
        withContext(Dispatchers.IO) {
            val newMovies = moviesService.getMovies(page = page).results.map { it.toLocalModel() }
            moviesDao.insertMovies(newMovies)
        }
    }

}

const val TIME_MOVIE_AVAILABLE = 24 * 60 * 60 * 1000