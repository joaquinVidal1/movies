package com.example.movies.presentation.movieDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.Result
import com.example.movies.domain.usecase.AddMovieToFavoriteUseCase
import com.example.movies.domain.usecase.GetMovieDetailsUseCase
import com.example.movies.presentation.destinations.MovieDetailsDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMovieDetailsUSeCase: GetMovieDetailsUseCase,
    private val addMovieToFavoriteUseCase: AddMovieToFavoriteUseCase
) : ViewModel() {

    private val movieId: Int = savedStateHandle[MovieDetailsDestination.movieIdArg]
        ?: throw IllegalStateException("No value passed for movieId")

    private val _uiState: MutableLiveData<MovieDetailsUiState> = MutableLiveData(MovieDetailsUiState.Loading)
    val uiState: LiveData<MovieDetailsUiState> = _uiState

    private val _isFav = MutableLiveData<Boolean>(isMovieFaved(movieId))
    val isFav: LiveData<Boolean> = _isFav

    init {
        getMovieDetails(movieId)
    }

    private fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            val movieDetails = getMovieDetailsUSeCase(params = GetMovieDetailsUseCase.Params(movieId = movieId))
            _uiState.value = if (movieDetails is Result.Error) {
                movieDetails.throwable?.let { MovieDetailsUiState.Error(it) }
            } else MovieDetailsUiState.Success((movieDetails as Result.Success).value)
        }
    }

    fun onFavoriteButtonPressed() {
        viewModelScope.launch {
            isFav.value?.let {
                if (it) {
                    //TODO
                } else {
                    addMovieToFavoriteUseCase(AddMovieToFavoriteUseCase.Params(movieId = movieId))
                }
                _isFav.value = !it
            }
        }
    }

    private fun isMovieFaved(movieId: Int): Boolean {
    }
}
