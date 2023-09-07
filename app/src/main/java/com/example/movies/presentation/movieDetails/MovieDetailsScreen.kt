package com.example.movies.presentation.movieDetails

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movies.R
import com.example.movies.presentation.movieDetails.components.GradientFloatingActionButton
import com.example.movies.presentation.movieDetails.components.MovieDetails

@Composable
fun MovieDetailsScreen(onBackPressed: () -> Unit, onShowReviewsPressed: () -> Unit) {

    val viewModel: MovieDetailsViewModel = hiltViewModel()
    val movie by viewModel.movie.collectAsState(initial = null)
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.systemMessage.collect { message ->
            message?.let { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() }
        }
    }

    movie?.let { movie ->

        Column(verticalArrangement = Arrangement.SpaceBetween) {

            MovieDetails(
                title = movie.title,
                peopleWatching = movie.peopleWatching,
                genres = movie.genres,
                voteAverage = movie.voteAverage,
                posterPath = movie.posterPath,
                videoPreviewPath = movie.videoPreviewPath,
                onBackPressed = onBackPressed,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = movie.overview,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            GradientFloatingActionButton(
                gradientColors = listOf(colorResource(id = R.color.orange), Color.Magenta),
                onClick = onShowReviewsPressed,
                elevation = 8.dp,
                modifier = Modifier
                    .padding(bottom = 50.dp)
                    .align(CenterHorizontally)
            ) {
                Text(
                    text = stringResource(id = R.string.show_reviews),
                    maxLines = 1,
                    style = MaterialTheme.typography.titleLarge.copy(color = Color.White, fontWeight = FontWeight.Bold),
                )
            }

        }

    }

}

const val MAX_VOTE = 10