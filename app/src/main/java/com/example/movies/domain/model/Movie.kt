package com.example.movies.domain.model

import com.example.movies.data.db.model.DBMovie
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val voteAverage: Double,
    val poster: String,
    val releaseDate: LocalDate,
) {

    fun toDBModel(pageNumber: Int): DBMovie = DBMovie(
        id = this.id,
        title = this.title,
        overview = this.overview,
        voteAverage = this.voteAverage,
        poster = this.poster,
        releaseDate = this.releaseDate,
        pageNumber = pageNumber
    )
}
