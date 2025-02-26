package com.example.movies.domain.model

import com.example.movies.data.db.model.DBMovie
import com.example.movies.domain.utils.LocalDateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val voteAverage: Double,
    val poster: String,
    val releaseYear: Int,
) {

    fun toDBModel(pageNumber: Int): DBMovie = DBMovie(
        id = this.id,
        title = this.title,
        overview = this.overview,
        voteAverage = this.voteAverage,
        poster = this.poster,
        releaseYear = this.releaseYear,
        pageNumber = pageNumber
    )
}
