package com.example.movies.presentation.home


sealed class HomeUiState {

    internal object Success: HomeUiState()
    internal class Error(val errorMessage: String) : HomeUiState()
    internal object Loading : HomeUiState()
}