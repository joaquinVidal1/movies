package com.example.movies.data.network.model

import com.squareup.moshi.Json

data class SpokenLanguagesItem(@Json(name = "name")
                               val name: String = "",
                               @Json(name = "iso_639_1")
                               val iso: String = "",
                               @Json(name = "english_name")
                               val englishName: String = "")