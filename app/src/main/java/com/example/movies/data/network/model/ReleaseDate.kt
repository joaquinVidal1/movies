package com.example.movies.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReleaseDate(
    @Json(name = "day") val day: Int,
    @Json(name = "month") val month: Int,
    @Json(name = "year") val year: Int
)