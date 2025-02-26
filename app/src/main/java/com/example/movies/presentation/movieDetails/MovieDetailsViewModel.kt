package com.example.movies.presentation.movieDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.movies.data.Result
import com.example.movies.domain.model.Movie
import com.example.movies.domain.usecase.GetMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle, private val getMovieDetailsUSeCase: GetMovieDetailsUseCase
) : ViewModel() {

    private val movie: Movie = savedStateHandle.toRoute()

    private val _uiState: MutableLiveData<MovieDetailsUiState> = MutableLiveData(
        MovieDetailsUiState.Loading(
            movie = movie
        )
    )
    val uiState: LiveData<MovieDetailsUiState> = _uiState

    init {
        getMovieDetails(movieId = movie.id)
    }

    private fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            val movieDetails =
                getMovieDetailsUSeCase(params = GetMovieDetailsUseCase.Params(movieId = movieId))
            _uiState.value = if (movieDetails is Result.Error) {
                movieDetails.throwable?.let { MovieDetailsUiState.Error(it) }
            } else MovieDetailsUiState.Success((movieDetails as Result.Success).value)
        }
    }
}
