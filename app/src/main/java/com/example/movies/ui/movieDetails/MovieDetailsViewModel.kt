package com.example.movies.ui.movieDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.MovieDetails
import com.example.movies.model.DetailsMovie
import com.example.movies.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle, private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _movie = MutableSharedFlow<DetailsMovie>(extraBufferCapacity = 1)
    val movie: Flow<DetailsMovie> get() = _movie

    private val movieId: Int =
        savedStateHandle[MovieDetails.movieIdArg] ?: throw IllegalStateException("No value passed for movieId")


    private val _systemMessage = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val systemMessage = _systemMessage.asSharedFlow()

    init {
        getMovieDetails()
    }

    private fun getMovieDetails() {
        viewModelScope.launch {
            try {
                _movie.emit(moviesRepository.getMovieDetails(movieId = movieId))
            }catch (e: Exception){
                _systemMessage.emit(e.message ?: e.toString())
            }
        }
    }
}