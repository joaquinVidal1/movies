package com.example.movies.presentation.search

import com.example.movies.domain.model.Movie

sealed class SearchUiState(val queryTitle: String) {

    internal class Success(val data: List<Movie>, query: String) : SearchUiState(query)

    internal class Error(val errorMessage: String, query: String) : SearchUiState(query)

    internal class Loading(query: String) : SearchUiState(query)
}
