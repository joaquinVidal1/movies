package com.example.movies.presentation.movieDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.Result
import com.example.movies.domain.model.DetailsMovie
import com.example.movies.domain.usecase.GetMovieDetailsUseCase
import com.example.movies.presentation.destinations.MovieDetailsDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle, private val getMovieDetailsUSeCase: GetMovieDetailsUseCase
) : ViewModel() {

    private val movieId: Int =
        savedStateHandle[MovieDetailsDestination.movieIdArg] ?: throw IllegalStateException("No value passed for movieId")

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
        val movieDetails = getMovieDetailsUSeCase(params = GetMovieDetailsUseCase.Params(movieId = movieId))
        return if (movieDetails is Result.Error) {
            _systemMessage.emit(movieDetails.message ?: movieDetails.toString())
            null
        } else (movieDetails as Result.Success).value
    }
}
