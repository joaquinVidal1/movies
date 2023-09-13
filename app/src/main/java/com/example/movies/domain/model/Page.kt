package com.example.movies.domain.model

data class Page(
    val number: Int, val savedTimeStamp: Long, val movies: List<Movie>
)