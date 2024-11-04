package com.example.movies.data.network.model

import com.squareup.moshi.Json

class IMDBIDResponse(
    val id: Int, @Json(name = "imdb_id") val imdbId: String
)