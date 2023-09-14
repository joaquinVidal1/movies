package com.example.movies.data.network.model

data class MovieFavoriteResponse(
    val success: Boolean,
    val statusCode: Int,
    val favorite: Boolean,
)