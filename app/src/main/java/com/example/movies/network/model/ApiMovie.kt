package com.example.movies.network.model

import com.example.movies.model.Movie
import com.example.movies.utils.DateUtils.fromBackendDateToLocalDate
import com.squareup.moshi.Json

data class ApiMovie(
    val overview: String,
    @Json(name = "original_language") val originalLanguage: String,
    @Json(name = "original_title") val originalTitle: String,
    val video: Boolean,
    val title: String,
    @Json(name = "genre_ids") val genreIds: List<Int>,
    @Json(name = "poster_path") val posterPath: String,
    @Json(name = "backdrop_path") val backdropPath: String,
    @Json(name = "release_date") val releaseDate: String,
    val popularity: Double,
    @Json(name = "vote_average") val voteAverage: Double,
    val id: Int,
    val adult: Boolean,
    @Json(name = "vote_count") val voteCount: Int
) {
    fun toLocalModel(): Movie = Movie(
        id = id.toString(),
        title = title,
        overview = overview,
        savedTimeStamp = System.currentTimeMillis(),
        releaseDate = releaseDate.fromBackendDateToLocalDate(),
        voteAverage = voteAverage,
        poster = MOVIE_IMAGE_BASE_URL + posterPath,
    )
}

const val MOVIE_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w200"