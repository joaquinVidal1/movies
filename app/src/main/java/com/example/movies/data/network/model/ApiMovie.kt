package com.example.movies.data.network.model

import com.example.movies.domain.model.Movie
import com.example.movies.domain.utils.DateUtils.fromBackendDateToLocalDate
import com.squareup.moshi.Json
import java.time.LocalDate

data class ApiMovie(
    val overview: String,
    @Json(name = "original_language") val originalLanguage: String,
    @Json(name = "original_title") val originalTitle: String,
    val video: Boolean,
    val title: String,
    @Json(name = "genre_ids") val genreIds: List<Int>,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "release_date") val releaseDate: String?,
    val popularity: Double,
    @Json(name = "vote_average") val voteAverage: Double,
    val id: Int,
    val adult: Boolean,
    @Json(name = "vote_count") val voteCount: Int
) {
    fun toLocalModel(): Movie = Movie(
        id = id,
        title = title,
        overview = overview,
        releaseDate = releaseDate?.fromBackendDateToLocalDate() ?: LocalDate.now(),
        voteAverage = voteAverage,
        poster = MOVIE_IMAGE_BASE_URL + posterPath,
    )
}

const val MOVIE_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w200"
const val MOVIE_IMAGE_BASE_URL_400 = "https://image.tmdb.org/t/p/w500"