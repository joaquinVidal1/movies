package com.example.movies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movies.ui.home.HomeScreen
import com.example.movies.ui.movieDetails.MovieDetailsScreen
import com.example.movies.ui.theme.MoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MoviesApp()
                }
            }
        }
    }
}

@Composable
fun MoviesApp() {
    MoviesTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController, startDestination = Home.route
        ) {

            composable(route = Home.route) {
                HomeScreen(onMoviePressed = {
                    navController.navigateToMovieDetails(it.id)
                })
            }

            composable(route = MovieDetails.routeWithArgs, arguments = MovieDetails.arguments) { navBackStackEntry ->
                MovieDetailsScreen(
                    onBackPressed = { navController.navigateUp() }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MoviesTheme {
        MoviesApp()
    }
}

private fun NavHostController.navigateToMovieDetails(movieId: Int) {
    this.navigate("${MovieDetails.route}/$movieId")
}