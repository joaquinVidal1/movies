package com.example.movies.presentation.movieDetails

import com.example.movies.domain.model.DetailsMovie

sealed class MovieDetailsUiState {

    internal class Success(val movie: DetailsMovie) : MovieDetailsUiState()

    internal class Error(val exception: Throwable) : MovieDetailsUiState()

    internal class Loading(val movie: DetailsMovie) : MovieDetailsUiState()
}