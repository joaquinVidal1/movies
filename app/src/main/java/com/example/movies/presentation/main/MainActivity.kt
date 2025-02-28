package com.example.movies.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movies.domain.model.Movie
import com.example.movies.presentation.destinations.HomeDestination
import com.example.movies.presentation.destinations.MovieReviewsDestination
import com.example.movies.presentation.destinations.MoviesNavigation
import com.example.movies.presentation.home.HomeScreen
import com.example.movies.presentation.movieDetails.MovieDetailsScreen
import com.example.movies.presentation.movieReviews.MovieReviewsScreen
import com.example.movies.presentation.theme.MoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoviesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    MoviesApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MoviesApp() {
    MoviesTheme {
        SharedTransitionLayout {
            val navController = rememberNavController()
            MoviesNavigation(navController)
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
