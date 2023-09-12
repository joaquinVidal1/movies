package com.example.movies.domain.repository

import androidx.lifecycle.LiveData
import com.example.movies.domain.model.DetailsMovie
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.MovieReview
import com.example.movies.domain.model.MovieReviews

interface MoviesRepository {

    val movies: LiveData<List<Movie>>

    suspend fun getMovies(page: Int)

    suspend fun getMovieDetails(movieId: Int): DetailsMovie

    suspend fun deleteExpiredMovies()

    suspend fun getMovieReviews(movieId: Int, page: Int): MovieReviews
}