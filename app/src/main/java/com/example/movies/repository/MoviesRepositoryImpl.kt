package com.example.movies.repository

import com.example.movies.db.MoviesDao
import com.example.movies.model.DetailsMovie
import com.example.movies.model.Movie
import com.example.movies.network.MoviesService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepositoryImpl @Inject constructor(
    private val moviesDao: MoviesDao, private val moviesService: MoviesService
): MoviesRepository {

  override suspend fun updateMovies(): List<Movie> {
    val minTimeStamp = System.currentTimeMillis() - TIME_MOVIE_AVAILABLE
    moviesDao.deleteExpiredMovies(deleteTimeMin = minTimeStamp)
    val newMovies = moviesService.getMovies(page = 1).results.map { it.toLocalModel() }
    moviesDao.insertMovies(newMovies)
    return moviesDao.getMovies()
  }

  override suspend fun getMoreMovies(page: Int): List<Movie> {
    val newMovies = moviesService.getMovies(page = page).results.map { it.toLocalModel() }
    moviesDao.insertMovies(newMovies)
    return moviesDao.getMovies()
  }

  override suspend fun getMovieDetails(movieId: Int): DetailsMovie {
    return moviesService.getMovieDetails(movieId).toModel()
  }

}

const val TIME_MOVIE_AVAILABLE = 24 * 60 * 60 * 1000