package com.example.movies.domain.repository

import com.example.movies.domain.model.DetailsMovie
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.MovieReviews
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    val movies: Flow<List<Movie>>

    suspend fun getMovies(currentMovies: Int)

    suspend fun getMovieDetails(movieId: Int): DetailsMovie

    suspend fun deleteExpiredMovies()

    suspend fun getMovieReviews(movieId: Int, page: Int): MovieReviews

    suspend fun emptyDatabase()
}