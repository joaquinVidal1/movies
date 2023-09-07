package com.example.movies.model

import com.example.movies.network.model.Genre

class DetailsMovie(
    val id: Int,
    val title: String,
    val peopleWatching: Int,
    val genres: List<String>,
    val voteAverage: Float,
    val overview: String,
    val posterPath: String,
    val videoPreviewPath: String,
)