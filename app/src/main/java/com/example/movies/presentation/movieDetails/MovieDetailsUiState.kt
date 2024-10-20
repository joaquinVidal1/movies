package com.example.movies.presentation.movieDetails

import com.example.movies.domain.model.DetailsMovie

sealed class MovieDetailsUiState {

    internal class Success(movie: DetailsMovie) : Data(movie)

    internal class Error(val exception: Throwable) : MovieDetailsUiState()

    internal class Loading(movie: DetailsMovie) : Data(movie)

    sealed class Data(val movie: DetailsMovie): MovieDetailsUiState()
}