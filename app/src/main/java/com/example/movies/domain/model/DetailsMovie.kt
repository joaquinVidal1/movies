package com.example.movies.domain.model

import com.example.movies.data.network.model.WatchProvider
import java.time.Duration
import java.time.LocalDate

data class DetailsMovie(
    val id: Int,
    val title: String,
    val peopleWatching: Int,
    val genres: List<String>,
    val voteAverage: Float,
    val overview: String,
    val posterPath: String,
    val videoPreviewPath: String,
    val watchProviders: List<WatchProvider>,
    val releaseDate: LocalDate,
    val duration: Duration,
    val budget: Long,
    val revenue: Long
)