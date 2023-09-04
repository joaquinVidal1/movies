package com.example.movies.model

import com.example.movies.network.model.Genre

class DetailsMovie(
    val title: String,
    val peopleWatching: Int,
    val genres: List<Genre>,
    val voteAverage: Float,
    val overview: String,
    val posterPath: String,
    val videoPreviewPath: String,
)