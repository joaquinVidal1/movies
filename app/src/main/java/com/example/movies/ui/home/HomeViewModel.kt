package com.example.movies.ui.home

import androidx.lifecycle.ViewModel
import com.example.movies.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val moviesRepository: MoviesRepository) : ViewModel() {

    val movies = moviesRepository.movies

    private val _systemMessage = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val systemMessage = _systemMessage.asSharedFlow()

    suspend fun updateMovies() {
        try {
            moviesRepository.updateMovies()
        } catch (e: Exception) {
            e.message?.let { _systemMessage.emit(it) }
        }
    }

}