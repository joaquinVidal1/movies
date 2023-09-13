package com.example.movies.domain.repository

import com.example.movies.domain.model.DetailsMovie
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.MovieReviews

interface MoviesRepository {

    suspend fun getAllMovies(): List<Movie>

    suspend fun getNextMoviesPage(): List<Movie>

    suspend fun getMovieDetails(movieId: Int): DetailsMovie

    suspend fun deleteExpiredMovies()

    suspend fun getMovieReviews(movieId: Int, page: Int): MovieReviews

    suspend fun emptyDatabase()
}