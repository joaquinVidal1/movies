package com.example.movies.network.model

import com.squareup.moshi.Json

data class ProductionCountriesItem(@Json(name = "iso_3166_1")
                                   val iso: String = "",
                                   @Json(name = "name")
                                   val name: String = "")