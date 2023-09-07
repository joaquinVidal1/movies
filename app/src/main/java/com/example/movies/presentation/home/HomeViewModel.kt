package com.example.movies.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import com.example.movies.domain.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val moviesRepository: MoviesRepository) : ViewModel() {

    val movies = moviesRepository.movies

    private var currentPage = 0

    private val _systemMessage = MutableLiveData<String?>(null)
    private val _isLoading = MutableLiveData(false)

    private val _uiState = MediatorLiveData<HomeUiState>(HomeUiState.Loading(listOf()))
    val uiState: LiveData<HomeUiState> = _uiState.distinctUntilChanged()

    init {
        updateMovies()

        _uiState.addSource(movies) {
            _uiState.value = HomeUiState.Success(it)
        }

        _uiState.addSource(_systemMessage) {
            _uiState.value = it?.let { HomeUiState.Error(errorMessage = it, data = movies.value ?: listOf()) }
        }

        _uiState.addSource(_isLoading) {
            _uiState.value = if (it) {
                HomeUiState.Loading(movies.value ?: listOf())
            } else {
                HomeUiState.Success(movies.value ?: listOf())
            }
        }
    }

    private fun updateMovies() {
        viewModelScope.launch {
            try {
                moviesRepository.updateMovies()
            } catch (e: Exception) {
                e.message?.let { _systemMessage.postValue(it) }
            }
            _isLoading.value = false
        }
    }

    suspend fun getMoreMovies() {
        _isLoading.value = true
        try {
            currentPage++
            moviesRepository.getMoreMovies(currentPage)
        } catch (e: Exception) {
            currentPage--
            e.message?.let { _systemMessage.postValue(it) }
        }
        _isLoading.value = false
    }

}