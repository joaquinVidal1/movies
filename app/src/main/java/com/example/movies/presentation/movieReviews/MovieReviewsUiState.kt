package com.example.movies.presentation.movieReviews

import com.example.movies.domain.model.MovieReview

sealed class MovieReviewsUiState(val reviews: List<MovieReview>) {

    internal class Success(data: List<MovieReview>) : MovieReviewsUiState(data)

    internal class Error(val errorMessage: String, data: List<MovieReview>) : MovieReviewsUiState(data)

    internal class Loading(data: List<MovieReview>) : MovieReviewsUiState(data)
}

