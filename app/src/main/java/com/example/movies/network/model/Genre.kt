package com.example.movies.network.model

import com.squareup.moshi.Json

data class Genre(
    @Json(name = "name") val name: String = "", @Json(name = "id") val id: Int = 0
)