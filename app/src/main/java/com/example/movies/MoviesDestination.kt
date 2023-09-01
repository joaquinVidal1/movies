package com.example.movies

interface MoviesDestination {
    val route: String
}

object Home: MoviesDestination {
    override val route: String = "home"
}

object MovieDetails: MoviesDestination {
    override val route: String = "movieDetails"
}