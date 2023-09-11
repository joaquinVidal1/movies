package com.example.movies.domain.model

data class MovieReview(
    val authorDetails: AuthorDetails,
    val updatedAt: String = "",
    val author: String = "",
    val createdAt: String = "",
    val id: String = "",
    val content: String = "",
    val url: String = ""
)