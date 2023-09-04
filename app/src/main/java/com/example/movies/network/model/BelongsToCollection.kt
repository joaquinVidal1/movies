package com.example.movies.network.model

import com.squareup.moshi.Json

data class BelongsToCollection(@Json(name = "backdrop_path")
                               val backdropPath: String = "",
                               @Json(name = "name")
                               val name: String = "",
                               @Json(name = "id")
                               val id: Int = 0,
                               @Json(name = "poster_path")
                               val posterPath: String = "")