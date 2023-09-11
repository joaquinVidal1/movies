package com.example.movies.presentation.destinations

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface MoviesDestination {
    val route: String
}

object Home : MoviesDestination {
    override val route: String = "home"
}

object MovieDetails : MoviesDestination {
    override val route: String = "movie_details"
    const val movieIdArg = "movie_id_arg"
    val routeWithArgs = "$route/{$movieIdArg}"
    val arguments = listOf(navArgument(movieIdArg) { type = NavType.IntType })
}

object MovieReviews : MoviesDestination {
    override val route: String = "movie_reviews"
    const val movieIdArg = "movie_id_arg"
    const val moviePosterPathArg = "movie_poster_path_arg"
    val routeWithArgs = "$route/{$movieIdArg}/{$moviePosterPathArg}"
    val arguments = listOf(
        navArgument(movieIdArg) { type = NavType.IntType },
        navArgument(moviePosterPathArg) { type = NavType.StringType }
    )
}