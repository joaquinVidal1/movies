package com.example.movies.presentation.movieDetails

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.movies.R
import com.example.movies.domain.model.DetailsMovie
import com.example.movies.presentation.movieDetails.components.GradientFloatingActionButton
import com.example.movies.presentation.movieDetails.components.MovieData
import com.example.movies.presentation.movieDetails.components.MovieVideoPreview

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalSharedTransitionApi
@Composable
fun SharedTransitionScope.MovieDetailsScreen(
    onBackPressed: () -> Unit,
    onShowReviewsPressed: (DetailsMovie) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope
) {

    val viewModel: MovieDetailsViewModel = hiltViewModel()
    val context = LocalContext.current
    val uiState by viewModel.uiState.observeAsState()

    Scaffold { contentPadding ->
        uiState?.let { state ->
            when (state) {
                is MovieDetailsUiState.Success -> {
                    Content(
                        movie = state.movie,
                        onBackPressed = onBackPressed,
                        onShowReviewsPressed = onShowReviewsPressed,
                        animatedVisibilityScope = animatedVisibilityScope,
                        modifier = Modifier.padding(contentPadding)
                    )
                }

                is MovieDetailsUiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(contentPadding)
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Center),
                            color = colorResource(id = R.color.orange)
                        )
                    }
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
    movie: DetailsMovie,
    onBackPressed: () -> Unit,
    onShowReviewsPressed: (DetailsMovie) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier
) {
    Column(verticalArrangement = Arrangement.SpaceBetween, modifier = modifier) {
//        MovieDetails(
//            title = movie.title,
//            peopleWatching = movie.peopleWatching,
//            genres = movie.genres,
//            voteAverage = movie.voteAverage,
//            posterPath = movie.posterPath,
//            videoPreviewPath = movie.videoPreviewPath,
//            onBackPressed = onBackPressed,
//            animatedVisibilityScope = animatedVisibilityScope
//        )
        Box {
            movie.run {
                MovieVideoPreview(
                    onBackPressed = onBackPressed,
                    moviePoster = videoPreviewPath,
                    modifier = Modifier.fillMaxWidth()
                )
                Row {
                    Image(
                        painter = rememberAsyncImagePainter(
                            posterPath,
                            placeholder = painterResource(id = R.drawable.movieplaceholder)
                        ),
                        modifier = Modifier
                            .shadow(8.dp)
                            .height(200.dp)
                            .sharedElement(state = rememberSharedContentState(key = "image/$id"),
                                animatedVisibilityScope = animatedVisibilityScope,
                                boundsTransform = { _, _ ->
                                    tween(durationMillis = 1000)
                                }),
                        contentDescription = stringResource(R.string.movie_poster),
                        contentScale = ContentScale.FillHeight,
                    )

                    Column {
                        Text(
                            text = title,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.fillMaxWidth()
                        )

                        MovieData(
                            peopleWatching = peopleWatching,
                            genres = genres,
                            vote = voteAverage,
                            modifier = Modifier.wrapContentHeight()
                        )
                    }
                }
            }
        }

        Text(
            text = movie.overview,
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(Modifier.weight(1f))

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

const val MAX_VOTE = 10