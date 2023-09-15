package com.example.movies.presentation.fav

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.Result
import com.example.movies.domain.model.Movie
import com.example.movies.domain.usecase.GetFavMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavsViewModel @Inject constructor(
    private val getAllFavsMoviesUseCase: GetFavMoviesUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<FavsUiState>(FavsUiState.Loading(listOf()))
    val uiState: StateFlow<FavsUiState> = _uiState

    init {
        getMovies()
    }

    private fun getMovies() {
        viewModelScope.launch {
            val response = getAllFavsMoviesUseCase(Unit)
            _uiState.value = if (response is Result.Success) {
                FavsUiState.Success(response.value)
            } else FavsUiState.Error(data = listOf(), errorMessage = (response as Result.Error).message ?: "Error")
        }

    }
}