package com.example.movies.presentation.movieDetails

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment.Companion.Center
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
import com.example.movies.domain.model.DetailsMovie
import com.example.movies.presentation.common.components.shimmerBrush
import com.example.movies.presentation.movieDetails.components.GradientFloatingActionButton
import com.example.movies.presentation.movieDetails.components.MovieDetails
import com.example.movies.presentation.search.components.Center

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.MovieDetailsScreen(
    onBackPressed: () -> Unit, onShowReviewsPressed: (DetailsMovie) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope
) {

    val viewModel: MovieDetailsViewModel = hiltViewModel()
    val context = LocalContext.current
    val uiState by viewModel.uiState.observeAsState()
    val isFav by viewModel.isFav.observeAsState(false)

    uiState?.let { state ->
        when (state) {
           is MovieDetailsUiState.Success,is MovieDetailsUiState.Loading -> {
                if (state is MovieDetailsUiState.Loading) {
                    Center {
                        Box {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Center),
                                color = colorResource(id = R.color.orange)
                            )
                        }
                    }
                }

                Content(
                    movie = state.movie,
                    onBackPressed = onBackPressed,
                    isFav = isFav,
                    animatedVisibilityScope = animatedVisibilityScope,
                    onShowReviewsPressed = onShowReviewsPressed,
                    onFavPressed = { viewModel.onFavoriteButtonPressed() },
                    movieId = viewModel.movieId,
                    launchedFrom =
                )
            }

            is MovieDetailsUiState.Error -> {
                Toast.makeText(context, state.exception.message, Toast.LENGTH_SHORT).show()
                onBackPressed()
            }

//            MovieDetailsUiState.Loading -> {
//                Box {
//                    CircularProgressIndicator(
//                        modifier = Modifier.align(Center),
//                        color = colorResource(id = R.color.orange)
//                    )
//                }
//            }
        }
    }

}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.Content(
    movie: DetailsMovie?,
    isFav: Boolean,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onBackPressed: () -> Unit,
    onShowReviewsPressed: (DetailsMovie) -> Unit,
    onFavPressed: () -> Unit,
    launchedFrom: String,
    movieId: Int
) {

    Column(verticalArrangement = Arrangement.SpaceBetween) {
        MovieDetails(
            title = movie?.title,
            peopleWatching = movie?.peopleWatching,
            genres = movie?.genres,
            voteAverage = movie?.voteAverage,
            posterPath = movie?.posterPath,
            videoPreviewPath = movie?.videoPreviewPath,
            onBackPressed = onBackPressed,
            isFav = isFav,
            onFavPressed = onFavPressed,
            watchProviders = movie?.watchProviders,
            modifier = Modifier,
            animatedVisibilityScope = animatedVisibilityScope,
            transitionKey = "$launchedFrom/${movieId}"
        )

        Text(
            text = movie?.overview ?: "",
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .background(shimmerBrush(movie == null)),
        )

        Spacer(modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.size(16.dp))

        if (movie != null) {
            GradientFloatingActionButton(
                gradientColors = listOf(colorResource(id = R.color.orange), Color.Magenta),
                onClick = { onShowReviewsPressed(movie) },
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

}

const val MAX_VOTE = 10