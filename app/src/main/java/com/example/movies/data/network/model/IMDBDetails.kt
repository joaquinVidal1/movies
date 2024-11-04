package com.example.movies.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.Duration
import java.time.LocalDate

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

val IMDBDetails.releaseDate: LocalDate
    get() = top.releaseDate.run { LocalDate.of(year, month, day) }

val IMDBDetails.budget: Long
    get() = main.productionBudget.budget.amount

val IMDBDetails.duration: Duration
    get() = Duration.ofSeconds(top.runtime.seconds)

val IMDBDetails.genres: List<String>
    get() = short.genre

val IMDBDetails.revenue: Long
    get() = main.lifetimeGross.total.amount

val IMDBDetails.voteAverage: Float
    get() = short.aggregateRating.ratingValue
