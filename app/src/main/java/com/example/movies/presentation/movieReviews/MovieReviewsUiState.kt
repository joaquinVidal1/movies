package com.example.movies.presentation.movieReviews

import com.example.movies.domain.model.MovieReview
import com.example.movies.domain.model.MovieReviews

sealed class MovieReviewsUiState(val reviews: MovieReviews) {

    internal class Success(data: MovieReviews) : MovieReviewsUiState(data)

    internal class Error(val errorMessage: String, data: MovieReviews) : MovieReviewsUiState(data)

    internal class Loading(data: MovieReviews) : MovieReviewsUiState(data)
}

