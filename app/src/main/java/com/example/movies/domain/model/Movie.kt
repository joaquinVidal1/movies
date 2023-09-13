package com.example.movies.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val voteAverage: Double,
    val poster: String,
    val releaseDate: LocalDate,
)
