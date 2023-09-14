package com.example.movies.data.network.model

data class MovieFavouriteRequestBody(
    val mediaType: String = "movie", val mediaId: Int, val favorite: Boolean
)