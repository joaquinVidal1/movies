package com.example.movies.presentation.main

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.example.movies.presentation.destinations.HomeDestination
import com.example.movies.presentation.destinations.MovieDetailsDestination
import com.example.movies.presentation.destinations.MovieReviewsDestination
import com.example.movies.presentation.destinations.MoviesNavHost
import com.example.movies.presentation.home.HomeScreen
import com.example.movies.presentation.movieDetails.MovieDetailsScreen
import com.example.movies.presentation.movieReviews.MovieReviewsScreen
import com.example.movies.presentation.theme.MoviesTheme
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
        MoviesNavHost(navController = navController)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MoviesTheme {
        MoviesApp()
    }
}
