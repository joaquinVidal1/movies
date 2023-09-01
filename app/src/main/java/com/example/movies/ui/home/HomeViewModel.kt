package com.example.movies.ui.home

import androidx.lifecycle.ViewModel
import com.example.movies.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val moviesRepository: MoviesRepository): ViewModel() {

    val movies = moviesRepository.movies

    suspend fun updateMovies(){
        moviesRepository.updateMovies()
    }

}