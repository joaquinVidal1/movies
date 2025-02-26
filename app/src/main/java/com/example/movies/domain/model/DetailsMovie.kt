package com.example.movies.domain.model

class DetailsMovie(
    val id: Int,
    val title: String,
    val peopleWatching: Int,
    val genres: List<String>,
    val voteAverage: Float,
    val overview: String,
    val posterPath: String,
    val videoPreviewPath: String,
    val releaseYear: Int
)

fun DetailsMovie.toModel(): Movie = Movie(
    id = id,
    title = title,
    overview = overview,
    voteAverage = voteAverage.toDouble(),
    poster = posterPath,
    releaseYear = releaseYear
)
