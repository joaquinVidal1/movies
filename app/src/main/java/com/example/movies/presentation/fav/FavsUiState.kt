package com.example.movies.presentation.fav

import com.example.movies.domain.model.Movie

sealed class FavsUiState(val data: List<Movie>) {

    class Success(data: List<Movie>) : FavsUiState(data)

    class Error(val errorMessage: String, data: List<Movie>) : FavsUiState(data)

    class Loading(data: List<Movie>) : FavsUiState(data)

}