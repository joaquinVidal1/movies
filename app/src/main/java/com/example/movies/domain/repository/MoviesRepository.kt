package com.example.movies.domain.repository

import com.example.movies.domain.model.DetailsMovie
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.MovieReviews
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun getAllMovies(): List<Movie>

    suspend fun getNextMoviesPage(): List<Movie>

    suspend fun getMovieDetails(movieId: Int): DetailsMovie

    suspend fun deleteExpiredMovies()

    suspend fun getMovieReviews(movieId: Int, page: Int): MovieReviews

    suspend fun emptyDatabase()

    suspend fun addMovieToFavorite(movieId: Int)

    suspend fun removeMovieFromFavorite(movieId: Int)

    suspend fun getMovieIsFaved(movieId: Int): Boolean

    fun getFavedMovies(): Flow<List<Movie>>

    suspend fun searchMovies(queryTitle: String): List<Movie>
}