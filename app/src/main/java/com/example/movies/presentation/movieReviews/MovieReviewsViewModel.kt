package com.example.movies.presentation.movieReviews

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.Result
import com.example.movies.domain.model.MovieReviews
import com.example.movies.domain.usecase.GetMovieReviewsUseCase
import com.example.movies.presentation.destinations.MovieReviewsDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieReviewsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle, private val getMovieReviewsUseCase: GetMovieReviewsUseCase
) : ViewModel() {

    private val movieId: Int = savedStateHandle.get<Int>(MovieReviewsDestination.movieIdArg)
        ?: throw IllegalArgumentException("No value passed for movieId")

    private var currentPage = 1
    private var totalPages: Int? = null

    private val _uiState = MutableStateFlow<MovieReviewsUiState>(
        MovieReviewsUiState.Loading(
            MovieReviews(
                amountOfReviews = 0, reviews = listOf(), totalPages = -1
            )
        )
    )
    val uiState: StateFlow<MovieReviewsUiState> = _uiState.asStateFlow()

    init {
        updateMovieReviews()
    }

    private fun updateMovieReviews() {
        viewModelScope.launch {
            _uiState.update {
                val result = getMovieReviewsUseCase(
                    GetMovieReviewsUseCase.Params(
                        movieId = movieId, page = currentPage
                    )
                )
                if (result is Result.Success) {
                    result.value.let { response ->
                        totalPages = response.totalPages
                        currentPage++
                        MovieReviewsUiState.Success(response)
                    }
                } else {
                    MovieReviewsUiState.Error(
                        errorMessage = (result as? Result.Error)?.message,
                        data = MovieReviews(0, listOf(), -1)
                    )
                }
            }
        }
    }

    fun getMoreReviews() {
        viewModelScope.launch {
            if (currentPage < (totalPages ?: -1)) {
                _uiState.update { currentState ->
                    _uiState.value = MovieReviewsUiState.Loading(_uiState.value.reviews)
                    currentPage++
                    val result = getMovieReviewsUseCase(
                        GetMovieReviewsUseCase.Params(
                            movieId = movieId, page = currentPage
                        )
                    )
                    if (result is Result.Error) {
                        currentPage--
                        MovieReviewsUiState.Error(
                            errorMessage = result.message,
                            data = currentState.reviews
                        )
                    } else {
                        MovieReviewsUiState.Success((result as Result.Success).value)
                    }
                }
            }
        }
    }
}
