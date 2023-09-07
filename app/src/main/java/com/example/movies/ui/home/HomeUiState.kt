package com.example.movies.ui.home

import com.example.movies.model.Movie


sealed class HomeUiState(val data: List<Movie>) {
    internal class Success(data: List<Movie>) : HomeUiState(data)
    internal class Error(val errorMessage: String, data: List<Movie>) : HomeUiState(data)
    internal class Loading(data: List<Movie>) : HomeUiState(data)
}