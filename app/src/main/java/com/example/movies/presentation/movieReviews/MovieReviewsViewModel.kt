package com.example.movies.presentation.movieReviews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import com.example.movies.data.Result
import com.example.movies.domain.model.MovieReview
import com.example.movies.domain.usecase.GetMovieReviewsUseCase
import com.example.movies.domain.utils.SingleLiveEvent
import com.example.movies.presentation.destinations.MovieReviews
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieReviewsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle, private val getMovieReviewsUseCase: GetMovieReviewsUseCase
) : ViewModel() {

    private val movieId: Int = savedStateHandle.get<Int>(MovieReviews.movieIdArg)
        ?: throw IllegalArgumentException("No value passed for movieId")

//    val posterPath: String = savedStateHandle.get<String>(MovieReviews.moviePosterPathArg)
//        ?: throw IllegalArgumentException("No value passed for poster path")

    private var currentPage = 1

    private val _movieReviews = MutableLiveData<List<MovieReview>>(listOf())
    private val _systemMessage = SingleLiveEvent<String>()
    private val _isLoading = MutableLiveData(false)

    private val _uiState = MediatorLiveData<MovieReviewsUiState>(MovieReviewsUiState.Loading(listOf()))
    val uiState: LiveData<MovieReviewsUiState> = _uiState.distinctUntilChanged()

    init {
        updateMovieReviews()

        _uiState.addSource(_movieReviews) {
            _uiState.value = MovieReviewsUiState.Success(it)
        }

        _uiState.addSource(_systemMessage) {
            _uiState.value =
                it?.let { MovieReviewsUiState.Error(errorMessage = it, data = _movieReviews.value ?: listOf()) }
        }

        _uiState.addSource(_isLoading) {
            _uiState.value = if (it) {
                MovieReviewsUiState.Loading(_movieReviews.value ?: listOf())
            } else {
                MovieReviewsUiState.Success(_movieReviews.value ?: listOf())
            }
        }
    }

    private fun updateMovieReviews() {
        viewModelScope.launch {
            val result = getMovieReviewsUseCase(GetMovieReviewsUseCase.Params(movieId = movieId, page = currentPage))
            if (result is Result.Success) {
                _movieReviews.postValue(result.value)
                currentPage++
            } else {
                _systemMessage.postValue((result as Result.Error).message)
            }
        }
    }

    suspend fun getMoreMovies() {
        _isLoading.value = true
        currentPage++
        val result = getMovieReviewsUseCase(GetMovieReviewsUseCase.Params(movieId = movieId, page = currentPage))
        if (result is Result.Error) {
            currentPage--
            result.message?.let { _systemMessage.postValue(it) }
        } else {
            _movieReviews.postValue((result as Result.Success).value)
        }
        _isLoading.value = false
    }


}



