package com.example.movies.presentation.destinations

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface MoviesDestination {
    val route: String
}

object Home: MoviesDestination {
    override val route: String = "home"
}

object MovieDetails: MoviesDestination {
    override val route: String = "movie_details"
    const val movieIdArg = "movie_id_arg"
    val routeWithArgs = "$route/{$movieIdArg}"
    val arguments = listOf(
        navArgument(movieIdArg) { type = NavType.IntType }
    )
}