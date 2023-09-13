package com.example.movies.presentation.home


sealed class HomeUiState {

sealed class HomeUiState(val data: List<Movie>) {

    internal class Success(data: List<Movie>) : HomeUiState(data)

    internal class Error(val errorMessage: String, data: List<Movie>) : HomeUiState(data)

    internal class Loading(data: List<Movie>) : HomeUiState(data)

    internal class ShowEmptyDbDialog(data: List<Movie>): HomeUiState(data)
}