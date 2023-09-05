package com.example.movies.repository

import androidx.room.Transaction
import com.example.movies.model.DetailsMovie
import com.example.movies.model.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    val movies: Flow<List<Movie>>

    @Transaction
    suspend fun updateMovies()

    suspend fun getMoreMovies(page: Int)

    suspend fun getMovieDetails(movieId: Int): DetailsMovie
}