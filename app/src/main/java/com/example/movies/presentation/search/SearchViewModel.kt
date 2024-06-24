package com.example.movies.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.Result
import com.example.movies.domain.usecase.SearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Success(listOf(), "", ""))
    val uiState: StateFlow<SearchUiState> = _uiState

    fun search(queryTitle: String, queryOverview: String) {
        _uiState.value = SearchUiState.Loading(queryTitle, queryOverview)
        viewModelScope.launch {
            _uiState.value = searchMoviesUseCase(
                SearchMoviesUseCase.Params(
                    queryTitle,
                    queryOverview
                )
            ).let { moviesResult ->
                if (moviesResult is Result.Error) {
                    moviesResult.message?.let {
                        SearchUiState.Error(errorMessage = it, queryTitle, queryOverview)
                    } ?: SearchUiState.Error(errorMessage = "null", queryTitle, queryOverview)
                } else {
                    SearchUiState.Success(
                        data = (moviesResult as Result.Success).value, queryTitle, queryOverview
                    )
                }
            }
        }
    }
}