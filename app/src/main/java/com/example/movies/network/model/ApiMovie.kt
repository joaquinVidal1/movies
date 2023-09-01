package com.example.movies.network.model

import com.example.movies.model.Movie
import com.example.movies.network.BASE_URL
import com.example.movies.utils.DateUtils.fromBackendDateToLocalDate

data class ApiMovie(
    val overview: String,
    val originalLanguage: String,
    val originalTitle: String,
    val video: Boolean,
    val title: String,
    val genreIds: List<Int>,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: String,
    val popularity: Double,
    val voteAverage: Double,
    val id: Int,
    val adult: Boolean,
    val voteCount: Int
) {
    fun toLocalModel(): Movie = Movie(
        id = id.toString(),
        title = title,
        overview = overview,
        savedTimeStamp = System.currentTimeMillis(),
        releaseDate = releaseDate.fromBackendDateToLocalDate(),
        voteAverage = voteAverage,
        poster = BASE_URL + posterPath,
    )
}