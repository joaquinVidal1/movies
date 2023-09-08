package com.example.movies.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import com.example.movies.data.Result
import com.example.movies.domain.usecase.DeleteExpiredMoviesUseCase
import com.example.movies.domain.usecase.LoadMoreMoviesUseCase
import com.example.movies.domain.usecase.ObserveMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val observeMoviesUseCase: ObserveMoviesUseCase,
    val loadMoviesUseCase: LoadMoreMoviesUseCase,
    private val deleteExpiredMoviesUseCase: DeleteExpiredMoviesUseCase
) : ViewModel() {

    val movies = observeMoviesUseCase()

    private var currentPage = 1

    private val _systemMessage = MutableLiveData<String?>(null)
    private val _isLoading = MutableLiveData(false)

    private val _uiState = MediatorLiveData<HomeUiState>(HomeUiState.Loading(listOf()))
    val uiState: LiveData<HomeUiState> = _uiState.distinctUntilChanged()

    init {
        updateMovies()

        _uiState.addSource(movies) {
            _uiState.value = HomeUiState.Success(it)
        }

        _uiState.addSource(_systemMessage) {
            _uiState.value = it?.let { HomeUiState.Error(errorMessage = it, data = movies.value ?: listOf()) }
        }

        _uiState.addSource(_isLoading) {
            _uiState.value = if (it) {
                HomeUiState.Loading(movies.value ?: listOf())
            } else {
                HomeUiState.Success(movies.value ?: listOf())
            }
        }
    }

    private fun updateMovies() {
        viewModelScope.launch {
            try {
                deleteExpiredMoviesUseCase.execute(Unit)
                loadMoviesUseCase.execute(LoadMoreMoviesUseCase.Params(currentPage))
            } catch (e: Exception) {
                e.message?.let { _systemMessage.postValue(it) }
            }
            _isLoading.value = false
        }
    }

    suspend fun getMoreMovies() {
        _isLoading.value = true
        currentPage++
        val result = loadMoviesUseCase(LoadMoreMoviesUseCase.Params(currentPage))
        if (result is Result.Error) {
            currentPage--
            result.message?.let { _systemMessage.postValue(it) }
        }
        _isLoading.value = false
    }

}