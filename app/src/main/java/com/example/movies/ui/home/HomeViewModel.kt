package com.example.movies.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val moviesRepository: MoviesRepository) : ViewModel() {

    val movies = moviesRepository.movies

    private var currentPage = 1

    private val _systemMessage = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val systemMessage = _systemMessage.asSharedFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: Flow<Boolean> = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            updateMovies()
        }
    }

    suspend fun updateMovies() {
        try {
            moviesRepository.updateMovies()
        } catch (e: Exception) {
            e.message?.let { _systemMessage.emit(it) }
        }
        _isLoading.value = false
    }

    suspend fun getMoreMovies() {
        _isLoading.value = true
        try {
            currentPage++
            moviesRepository.getMoreMovies(currentPage)
        } catch (e: Exception) {
            e.message?.let { _systemMessage.emit(it) }
        }
        _isLoading.value = false
    }

}