package com.example.movies.ui.movieDetails

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movies.ui.movieDetails.components.MoviePoster

@Composable
fun MovieDetailsScreen(movieId: Int, onBackPressed: () -> Unit) {

    val viewModel: MovieDetailsViewModel = hiltViewModel()
    val posterPath = viewModel.posterPath.collectAsState(initial = null)
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.systemMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    Column {
        posterPath.value?.let {
            MoviePoster(
                onBackPressed = onBackPressed, moviePoster = it
            )
        }
    }

}


const val MAX_VOTE = 10