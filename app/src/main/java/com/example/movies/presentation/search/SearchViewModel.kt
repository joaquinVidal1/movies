package com.example.movies.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.Result
import com.example.movies.domain.model.Movie
import com.example.movies.domain.usecase.GetNextMoviesPageUseCase
import com.example.movies.domain.usecase.SearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val getNextMoviesUseCase: GetNextMoviesPageUseCase,
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<SearchUiState>(SearchUiState.Success(data = listOf(), query = ""))
    val uiState: StateFlow<SearchUiState> = _uiState

    private val currentMovies: List<Movie>?
        get() = (uiState.value as? SearchUiState.Success)?.data

    private val _searchHandler = uiState.map { it.queryTitle }.debounce(2000).onEach {
        makeSearch(it)
    }.launchIn(viewModelScope)

    fun search(queryTitle: String) {
        _uiState.value = SearchUiState.Loading(query = queryTitle)
    }

    private fun makeSearch(queryTitle: String) {
        viewModelScope.launch {
            _uiState.value = searchMoviesUseCase(
                SearchMoviesUseCase.Params(queryTitle)
            ).let { moviesResult ->
                if (moviesResult is Result.Error) {
                    moviesResult.message?.let {
                        SearchUiState.Error(errorMessage = it, queryTitle)
                    } ?: SearchUiState.Error(errorMessage = "null", queryTitle)
                } else {
                    SearchUiState.Success(
                        data = (moviesResult as Result.Success).value, queryTitle
                    )
                }
            }
        }
    }

    suspend fun loadMoreMovies() {
        _uiState.value = SearchUiState.Success(
            data = currentMovies ?: emptyList(),
            query = uiState.value.queryTitle,
            smallLoading = true
        )
        val newMovies = getNextMoviesUseCase(Unit)
        if (newMovies is Result.Error) {
            newMovies.message?.let {
                _uiState.value = SearchUiState.Error(
                    errorMessage = "Please try again", query = uiState.value.queryTitle
                )
            }
        } else {
//            _uiState.value =
//                SearchUiState.Success(currentMovies + (newMovies as Result.Success).value)
        }
    }
}