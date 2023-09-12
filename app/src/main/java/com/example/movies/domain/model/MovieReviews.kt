package com.example.movies.domain.model

data class MovieReviews(
    val amountOfReviews: Int, val reviews: List<MovieReview>, val totalPages: Int
)