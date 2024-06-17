package com.example.movies.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.Result
import com.example.movies.domain.model.Movie
import com.example.movies.domain.usecase.DeleteExpiredMoviesUseCase
import com.example.movies.domain.usecase.EmptyDatabaseUseCase
import com.example.movies.domain.usecase.GetAllMoviesUseCase
import com.example.movies.domain.usecase.GetNextMoviesPageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllMoviesUseCase: GetAllMoviesUseCase,
    private val deleteExpiredMoviesUseCase: DeleteExpiredMoviesUseCase,
    private val emptyDatabaseUseCase: EmptyDatabaseUseCase,
    private val getNextMoviesUseCase: GetNextMoviesPageUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading(listOf()))
    val uiState: StateFlow<HomeUiState> = _uiState

    private val currentMovies: List<Movie>
        get() = uiState.value.data

    init {
        updateMovies()
    }

    private fun updateMovies() {
        viewModelScope.launch {
            val resultOfDeleting = deleteExpiredMoviesUseCase(Unit)
            val movies = getAllMoviesUseCase(Unit)
            when {
                resultOfDeleting is Result.Error -> resultOfDeleting.message?.let {
                    _uiState.value = HomeUiState.Error(data = currentMovies, errorMessage = it)
                }

                movies is Result.Error -> movies.message?.let {
                    _uiState.value = HomeUiState.Error(data = currentMovies, errorMessage = it)
                }

                else -> {
                    val moviesList = (movies as Result.Success).value
                    if (moviesList.isEmpty()) {
                        getMoreMovies()
                    } else {
                        _uiState.value = HomeUiState.Success(data = moviesList)
                    }
                }
            }
        }
    }

    suspend fun getMoreMovies() {
        _uiState.value = HomeUiState.Loading(currentMovies)
        val newMovies = getNextMoviesUseCase(Unit)
        if (newMovies is Result.Error) {
            newMovies.message?.let {
                _uiState.value = HomeUiState.Error(data = currentMovies, errorMessage = it)
            }
        } else {
            _uiState.value =
                HomeUiState.Success(currentMovies + (newMovies as Result.Success).value)
        }
    }

    fun onEmptyPressed() {
        _uiState.value = HomeUiState.ShowEmptyDbDialog(currentMovies)
    }

    fun onConfirmEmptyDatabase() {
        _uiState.value = HomeUiState.Loading(listOf())
        viewModelScope.launch {
            emptyDatabaseUseCase(Unit)
            getMoreMovies()
        }
    }

    fun onCloseDialog() {
        _uiState.value = HomeUiState.Success(currentMovies)
    }

}