package com.example.movies.presentation.movieDetails

import com.example.movies.domain.model.DetailsMovie

sealed class MovieDetailsUiState {

    internal class Success(val movie: DetailsMovie) : MovieDetailsUiState()

    internal class Error(val exception: Throwable) : MovieDetailsUiState()

    internal object Loading : MovieDetailsUiState()
}