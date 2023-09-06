package com.example.movies.repository

import androidx.room.Transaction
import com.example.movies.model.DetailsMovie
import com.example.movies.model.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    @Transaction
    suspend fun updateMovies(): List<Movie>

    suspend fun getMoreMovies(page: Int): List<Movie>

    suspend fun getMovieDetails(movieId: Int): DetailsMovie
}