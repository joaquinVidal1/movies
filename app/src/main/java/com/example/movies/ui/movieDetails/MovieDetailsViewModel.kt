package com.example.movies.ui.movieDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.MovieDetails
import com.example.movies.model.DetailsMovie
import com.example.movies.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle, private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val movieId: Int =
        savedStateHandle[MovieDetails.movieIdArg] ?: throw IllegalStateException("No value passed for movieId")

    val movie: Flow<DetailsMovie> = callbackFlow {
        viewModelScope.launch {
            getMovieDetails(movieId)?.let {
                trySend(it)
            }
        }

        awaitClose { channel.close() }
    }

    private val _systemMessage = MutableStateFlow<String?>(value = null)
    val systemMessage: Flow<String?> = _systemMessage

    private suspend fun getMovieDetails(movieId: Int): DetailsMovie? {
        return try {
            moviesRepository.getMovieDetails(movieId = movieId)
        } catch (e: Exception) {
            _systemMessage.emit(e.message ?: e.toString())
            null
        }
    }
}
