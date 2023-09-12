package com.example.movies.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.Result
import com.example.movies.domain.model.Movie
import com.example.movies.domain.usecase.DeleteExpiredMoviesUseCase
import com.example.movies.domain.usecase.LoadMoviesUseCase
import com.example.movies.domain.usecase.ObserveMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val observeMoviesUseCase: ObserveMoviesUseCase,
    private val loadMoviesUseCase: LoadMoviesUseCase,
    private val deleteExpiredMoviesUseCase: DeleteExpiredMoviesUseCase
) : ViewModel() {

    val movies: Flow<List<Movie>> = observeMoviesUseCase()

    private var currentPage = 1

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        updateMovies()
    }

    private fun updateMovies() {
        viewModelScope.launch {
            val resultOfDeleting = deleteExpiredMoviesUseCase(Unit)
            val resultOfLoading = loadMoviesUseCase(LoadMoviesUseCase.Params(currentPage))
            when {
                resultOfDeleting is Result.Error -> resultOfDeleting.message?.let {
                    _uiState.value = HomeUiState.Error(it)
                }

                resultOfLoading is Result.Error -> resultOfLoading.message?.let {
                    _uiState.value = HomeUiState.Error(it)
                }

                else -> _uiState.value = HomeUiState.Success
            }
        }
    }

    suspend fun getMoreMovies(currentMoviesSize: Int) {
        _uiState.value = HomeUiState.Loading
        currentPage++
        val result = loadMoviesUseCase(LoadMoviesUseCase.Params(currentMoviesSize))
        if (result is Result.Error) {
            currentPage--
            result.message?.let { _uiState.value = HomeUiState.Error(it) }
        } else {
            _uiState.value = HomeUiState.Success
        }
    }

}