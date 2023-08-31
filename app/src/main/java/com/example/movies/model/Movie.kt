package com.example.movies.model

import java.time.LocalDate

data class Movie(
    val id: String,
    val title: String,
    val overview: String,
    val voteAverage: Double,
    val poster: String,
    val releaseDate: LocalDate,
)
