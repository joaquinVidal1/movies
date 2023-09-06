package com.example.movies.ui.home

import com.example.movies.model.Movie

sealed class HomeUiState {
  internal class Success(val data: List<Movie>): HomeUiState()
  internal class Error(val exception: Exception): HomeUiState()
  internal object Loading: HomeUiState()
}