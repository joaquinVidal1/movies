package com.example.movies.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val moviesRepository: MoviesRepository):
    ViewModel() {

  private var currentPage = 0

  private val _uiStateFlow = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
  val uiStateFlow: StateFlow<HomeUiState> = _uiStateFlow

  init {
    updateMovies()
  }

  private fun updateMovies() {
    viewModelScope.launch(Dispatchers.IO) {
      try {
        val movies = moviesRepository.updateMovies()
        _uiStateFlow.emit(HomeUiState.Success(movies))
      } catch (e: Exception) {
        _uiStateFlow.emit(HomeUiState.Error(e))
      }
    }
  }

  suspend fun getMoreMovies() {
    viewModelScope.launch(Dispatchers.IO) {
      try {
        currentPage++
        val newMovies = moviesRepository.getMoreMovies(currentPage)
        _uiStateFlow.emit(HomeUiState.Success(newMovies))
      } catch (e: Exception) {
        _uiStateFlow.emit(HomeUiState.Error(e))
      }
    }
  }
}
