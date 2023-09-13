package com.example.movies.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import com.example.movies.data.Result
import com.example.movies.domain.usecase.DeleteExpiredMoviesUseCase
import com.example.movies.domain.usecase.EmptyDatabaseUseCase
import com.example.movies.domain.usecase.LoadMoviesUseCase
import com.example.movies.domain.usecase.ObserveMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val observeMoviesUseCase: ObserveMoviesUseCase,
    private val loadMoviesUseCase: LoadMoviesUseCase,
    private val deleteExpiredMoviesUseCase: DeleteExpiredMoviesUseCase,
    private val emptyDatabaseUseCase: EmptyDatabaseUseCase
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
            val resultOfDeleting = deleteExpiredMoviesUseCase(Unit)
            val resultOfLoading = loadMoviesUseCase(LoadMoviesUseCase.Params(currentPage))
            when {
                resultOfDeleting is Result.Error -> resultOfDeleting.message?.let { _systemMessage.postValue(it) }
                resultOfLoading is Result.Error -> resultOfLoading.message?.let { _systemMessage.postValue(it) }
            }

            _isLoading.value = false
        }
    }

    suspend fun getMoreMovies() {
        _isLoading.value = true
        currentPage++
        val result = loadMoviesUseCase(LoadMoviesUseCase.Params(currentPage))
        if (result is Result.Error) {
            currentPage--
            result.message?.let { _systemMessage.postValue(it) }
        }
        _isLoading.value = false
    }

    fun onEmptyPressed() {
        _uiState.value = HomeUiState.ShowEmptyDbDialog(uiState.value?.data ?: listOf())
    }

    fun onConfirmEmptyDatabase() {
        _isLoading.value = true
        viewModelScope.launch {
            emptyDatabaseUseCase(Unit)
            currentPage = 0
            getMoreMovies()
        }
    }

    fun onCloseDialog() {
        _uiState.value = HomeUiState.Success(uiState.value?.data ?: listOf())
    }

}