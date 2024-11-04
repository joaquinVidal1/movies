package com.example.movies.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class IMDBDetails(
    @Json(name = "ok") val ok: Boolean,
    @Json(name = "top") val top: Top,
    @Json(name = "short") val short: Short,
    @Json(name = "main") val main: Main
)

data class Top(val runtime: Runtime, val releaseDate: ReleaseDate)

data class Short(val genre: List<String>, val aggregateRating: Rating)

data class Main(val productionBudget: Budget, val lifetimeGross: LifetimeGross)
