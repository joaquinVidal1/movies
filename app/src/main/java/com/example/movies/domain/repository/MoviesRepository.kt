package com.example.movies.domain.repository

import androidx.lifecycle.LiveData
import androidx.room.Transaction
import com.example.movies.domain.model.DetailsMovie
import com.example.movies.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    val movies: LiveData<List<Movie>>
    @Transaction

    suspend fun updateMovies()
    suspend fun getMoreMovies(page: Int)

    suspend fun getMovieDetails(movieId: Int): DetailsMovie
}