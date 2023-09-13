package com.example.movies.data.db.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.movies.domain.model.Page
import java.time.LocalDate

@Entity(
    foreignKeys = [ForeignKey(
        entity = Page::class, parentColumns = arrayOf("number"), childColumns = arrayOf("pageNumber")
    )]
)
data class DBbMovie(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String,
    val voteAverage: Double,
    val poster: String,
    val releaseDate: LocalDate,
    val pageNumber: Int
)