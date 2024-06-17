package com.example.movies.presentation.fav

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.domain.usecase.GetFavMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FavsViewModel @Inject constructor(
    private val getAllFavsMoviesUseCase: GetFavMoviesUseCase,
) : ViewModel() {

    val uiState: StateFlow<FavsUiState> = getAllFavsMoviesUseCase().map { FavsUiState.Success(it) }
        .stateIn(viewModelScope, SharingStarted.Lazily, FavsUiState.Loading(listOf()))

}
