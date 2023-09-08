package com.example.movies.domain.repository

import androidx.lifecycle.LiveData
import com.example.movies.domain.model.DetailsMovie
import com.example.movies.domain.model.Movie

interface MoviesRepository {

    val movies: LiveData<List<Movie>>

    suspend fun getMovies(page: Int)

    suspend fun getMovieDetails(movieId: Int): DetailsMovie

    suspend fun deleteExpiredMovies()
}