package com.example.movies.network.model

import com.squareup.moshi.Json

data class ProductionCompaniesItem(@Json(name = "logo_path")
                                   val logoPath: String = "",
                                   @Json(name = "name")
                                   val name: String = "",
                                   @Json(name = "id")
                                   val id: Int = 0,
                                   @Json(name = "origin_country")
                                   val originCountry: String = "")