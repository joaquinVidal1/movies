package com.example.movies

interface MoviesDestination {
    val route: String
}

object Home: MoviesDestination {
    override val route: String = "home"
}