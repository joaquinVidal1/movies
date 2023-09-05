package com.example.movies.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
data class Movie(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String,
    val voteAverage: Double,
    val poster: String,
    val releaseDate: LocalDate,
    val savedTimeStamp: Long,
)
