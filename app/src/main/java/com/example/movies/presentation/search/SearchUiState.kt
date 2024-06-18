package com.example.movies.presentation.search

import com.example.movies.domain.model.Movie

sealed class SearchUiState(val queryTitle: String, val queryOverview: String) {

    internal class Success(val data: List<Movie>, query: String, queryOverview: String) :
        SearchUiState(query, queryOverview)

    internal class Error(val errorMessage: String, query: String, queryOverview: String) :
        SearchUiState(query, queryOverview)

    internal class Loading(query: String, queryOverview: String) :
        SearchUiState(query, queryOverview)
}