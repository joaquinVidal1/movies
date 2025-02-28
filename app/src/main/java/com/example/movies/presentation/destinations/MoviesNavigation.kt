package com.example.movies.presentation.destinations

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.movies.domain.model.Movie
import com.example.movies.presentation.home.HomeScreen
import com.example.movies.presentation.movieDetails.MovieDetailsScreen
import com.example.movies.presentation.movieReviews.MovieReviewsScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.MoviesNavigation(navController: NavHostController) {
    NavHost(
        navController = navController, startDestination = HomeDestination.route
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(onMoviePressed = {
                navController.navigateToMovieDetails(it)
            }, animatedVisibilityScope = this)
        }

        composable<Movie> {
            MovieDetailsScreen(onBackPressed = { navController.navigateUp() },
                onShowReviewsPressed = { movieId ->
                    navController.navigateToReviews(
                        movieId = movieId
                    )
                },
                animatedVisibilityScope = this
            )
        }

        composable(
            route = MovieReviewsDestination.routeWithArgs,
            arguments = MovieReviewsDestination.arguments
        ) {
            MovieReviewsScreen(
                onBackPressed = { navController.navigateUp() }, animatedVisibilityScope = this
            )
        }
    }
}

private fun NavHostController.navigateToMovieDetails(movie: Movie) {
    this.navigate(route = movie)
}

private fun NavHostController.navigateToReviews(movieId: Int) {
    this.navigate("${MovieReviewsDestination.route}/$movieId")
}
