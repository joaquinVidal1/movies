package com.example.movies.presentation.destinations

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.movies.presentation.fav.FavScreen
import com.example.movies.presentation.home.HomeScreen
import com.example.movies.presentation.movieDetails.MovieDetailsScreen
import com.example.movies.presentation.movieReviews.MovieReviewsScreen
import com.example.movies.presentation.search.SearchScreen

@Composable
fun MoviesNavHost(
    navController: NavHostController, modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController, startDestination = HomeDestination.route, modifier = modifier
    ) {

        composable(route = HomeDestination.route) {
            HomeScreen(onMoviePressed = {
                navController.navigateToMovieDetails(it.id)
            })
        }

        composable(
            route = MovieDetailsDestination.routeWithArgs,
            arguments = MovieDetailsDestination.arguments
        ) { _ ->
            MovieDetailsScreen(onBackPressed = { navController.navigateUp() },
                onShowReviewsPressed = { movie ->
                    navController.navigateToReviews(
                        movieId = movie.id, moviePoster = movie.posterPath
                    )
                })
        }

        composable(
            route = MovieReviewsDestination.routeWithArgs,
            arguments = MovieReviewsDestination.arguments
        ) { navBackStackEntry ->
            MovieReviewsScreen(
                onBackPressed = { navController.navigateUp() },
                posterPath = navBackStackEntry.arguments?.getString(MovieReviewsDestination.moviePosterPathArg)
                    ?: throw Exception("No value passed for movie poster")
            )
        }

        composable(route = MovieReviewsDestination.FavsDestination.route) {
            FavScreen(onMoviePressed = { navController.navigateToMovieDetails(it.id) })
        }

        composable(route = SearchDestination.route) {
            SearchScreen(onMoviePressed = { navController.navigateToMovieDetails(it.id) })
        }
    }
}

private fun NavHostController.navigateToMovieDetails(movieId: Int) {
    this.navigate("${MovieDetailsDestination.route}/$movieId")
}

private fun NavHostController.navigateToReviews(movieId: Int, moviePoster: String) {
    val encodedPosterPath = Uri.encode(moviePoster)
    this.navigate("${MovieReviewsDestination.route}/$movieId/$encodedPosterPath")
}

private fun NavHostController.navigateToSearch() {
    this.navigate(SearchDestination.route)
}