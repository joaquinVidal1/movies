package com.example.movies.presentation.movieDetails.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberAsyncImagePainter
import com.example.movies.R
import com.example.movies.data.network.model.WatchProvider
import com.example.movies.presentation.common.components.shimmerBrush
import com.example.movies.presentation.theme.MoviesTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.MovieDetails(
    title: String?,
    peopleWatching: Int?,
    genres: List<String>?,
    voteAverage: Float?,
    posterPath: String?,
    videoPreviewPath: String?,
    watchProviders: List<WatchProvider>?,
    onBackPressed: () -> Unit,
    isFav: Boolean,
    onFavPressed: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
    transitionKey: String,
) {
    ConstraintLayout(
        modifier = modifier,
    ) {

        val videoPreview = createRef()
        val moviePoster = createRef()
        val movieData = createRef()
        val movieTitle = createRef()

        MovieVideoPreview(
            onBackPressed = onBackPressed,
            moviePoster = videoPreviewPath,
            modifier = Modifier
                .constrainAs(videoPreview) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            isFav = isFav,
            onFavPressed = onFavPressed
        )

        Image(
            painter = rememberAsyncImagePainter(
                posterPath,
            ),
            modifier = Modifier
                .shadow(8.dp)
                .height(200.dp)
                .constrainAs(moviePoster) {
                    start.linkTo(parent.start, margin = 24.dp)
                    top.linkTo(videoPreview.bottom, margin = (-70).dp)
                }
                .background(shimmerBrush(showShimmer = posterPath == null))
                .sharedElement(
                    state = rememberSharedContentState(key = transitionKey),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = { _, _ ->
                        tween(durationMillis = 1000)
                    }
                ),
            contentDescription = stringResource(R.string.movie_poster),
            contentScale = ContentScale.FillHeight,
        )

        Text(text = title ?: "",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(movieTitle) {
                    bottom.linkTo(videoPreview.bottom, margin = 16.dp)
                    start.linkTo(moviePoster.end, margin = 16.dp)
                    end.linkTo(parent.end, margin = 8.dp)
                    width = Dimension.fillToConstraints
                }
                .background(shimmerBrush(showShimmer = title == null))
        )

        MovieData(peopleWatching = peopleWatching,
            genres = genres,
            vote = voteAverage,
            watchProviders = watchProviders,
            modifier = Modifier
                .wrapContentHeight()
                .constrainAs(movieData) {
                    start.linkTo(moviePoster.end, margin = 16.dp)
                    top.linkTo(videoPreview.bottom, margin = 16.dp)
                })

    }
}

//@Preview
//@Composable
//fun MovieDetailsPreview() {
//    MoviesTheme {
//        MovieDetails(
//            title = "Justice League",
//            peopleWatching = 3292,
//            genres = listOf("Action", "Adventure", "Fantast"),
//            voteAverage = 9.8f,
//            posterPath = "",
//            videoPreviewPath = "",
//            onBackPressed = {},
//            isFav = false,
//            onFavPressed = {},
//            watchProviders = listOf()
//        )
//    }
//}