package com.example.movies.repository

import androidx.room.Transaction
import com.example.movies.db.MoviesDao
import com.example.movies.model.Movie
import com.example.movies.network.MoviesService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepositoryImpl @Inject constructor(
    private val moviesDao: MoviesDao, private val moviesService: MoviesService
) : MoviesRepository {

    override val movies: Flow<List<Movie>> = moviesDao.getMovies()

    @Transaction
    override suspend fun updateMovies() {
        val minTimeStamp = System.currentTimeMillis() - TIME_MOVIE_AVAILABLE
        moviesDao.deleteExpiredMovies(deleteTimeMin = minTimeStamp)
        val newMovies = moviesService.getMovies(page = 1).map { it.toLocalModel() }
        moviesDao.insertMovies(newMovies)
    }

    override suspend fun getMoreMovies(page: Int) {
        val newMovies = moviesService.getMovies(page = page).map { it.toLocalModel() }
        moviesDao.insertMovies(newMovies)
    }

}

const val TIME_MOVIE_AVAILABLE = 24 * 60 * 60 * 1000