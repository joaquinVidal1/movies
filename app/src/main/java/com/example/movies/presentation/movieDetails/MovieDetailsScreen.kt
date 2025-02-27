package com.example.movies.presentation.movieDetails

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.toModel
import com.example.movies.presentation.movieDetails.components.GradientFloatingActionButton
import com.example.movies.presentation.movieDetails.components.MovieDetails

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalSharedTransitionApi
@Composable
fun SharedTransitionScope.MovieDetailsScreen(
    onBackPressed: () -> Unit,
    onShowReviewsPressed: (Int) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {

    val viewModel: MovieDetailsViewModel = hiltViewModel()
    val context = LocalContext.current
    val uiState by viewModel.uiState.observeAsState()

    Scaffold { contentPadding ->
        uiState?.let { state ->
            when (state) {
                is MovieDetailsUiState.Success -> {
                    state.movie.run {
                        Content(
                            movie = this.toModel(),
                            onBackPressed = onBackPressed,
                            onShowReviewsPressed = onShowReviewsPressed,
                            animatedVisibilityScope = animatedVisibilityScope,
                            modifier = Modifier.padding(contentPadding),
                            peopleWatching = peopleWatching,
                            genres = genres,
                            videoPreviewPath = videoPreviewPath,
                        )
                    }
                }

                is MovieDetailsUiState.Loading -> {
                    Content(
                        movie = state.movie,
                        onBackPressed = onBackPressed,
                        onShowReviewsPressed = onShowReviewsPressed,
                        animatedVisibilityScope = animatedVisibilityScope,
                        modifier = Modifier.padding(contentPadding),
                        peopleWatching = null,
                        genres = null,
                        videoPreviewPath = null,
                    )
                }

                is MovieDetailsUiState.Error -> {
                    Toast.makeText(context, state.exception.message, Toast.LENGTH_SHORT).show()
                    onBackPressed()
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.Content(
    movie: Movie,
    peopleWatching: Int?,
    genres: List<String>?,
    onBackPressed: () -> Unit,
    videoPreviewPath: String?,
    onShowReviewsPressed: (Int) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier
) {
    Column(verticalArrangement = Arrangement.SpaceBetween, modifier = modifier) {
        MovieDetails(
            title = movie.title,
            peopleWatching = peopleWatching,
            genres = genres,
            voteAverage = movie.voteAverage.toFloat(),
            posterPath = movie.poster,
            videoPreviewPath = videoPreviewPath,
            onBackPressed = onBackPressed,
            animatedVisibilityScope = animatedVisibilityScope,
            movieId = movie.id
        )

        Text(
            text = movie.overview,
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
        )

        Spacer(Modifier.weight(1f))

        GradientFloatingActionButton(
            gradientColors = listOf(colorResource(id = R.color.orange), Color.Magenta),
            onClick = { onShowReviewsPressed(movie.id) },
            elevation = 8.dp,
            modifier = Modifier
                .padding(bottom = 40.dp)
                .align(CenterHorizontally)
        ) {
            Text(
                text = stringResource(id = R.string.show_reviews),
                maxLines = 1,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.White, fontWeight = FontWeight.Bold
                ),
            )
        }
    }
}

const val MAX_VOTE = 10
