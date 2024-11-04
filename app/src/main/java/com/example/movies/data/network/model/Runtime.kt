package com.example.movies.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Runtime(
    @Json(name = "seconds") val seconds: Int
)