package com.example.movies.ui.movieDetails.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import com.example.movies.R
import com.example.movies.ui.theme.MoviesTheme

@Composable
fun MoviePoster(onBackPressed: () -> Unit, moviePoster: String, modifier: Modifier = Modifier) {
    Box {

        BackButton(color = Color.White, modifier = Modifier.align(Alignment.TopStart)) {
            onBackPressed()
        }

        Image(
            painter = rememberAsyncImagePainter(
                moviePoster, placeholder = painterResource(id = R.drawable.movieplaceholder)
            ),
            contentDescription = stringResource(R.string.movie_poster),
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            contentScale = ContentScale.Crop,
        )

        Image(
            imageVector = Icons.Filled.PlayArrow,
            contentDescription = null,
            colorFilter = ColorFilter.tint(colorResource(id = R.color.orange))
        )

    }
}


@Preview
@Composable
fun MoviePosterPreview() {
    MoviesTheme {
        MoviePoster(onBackPressed = {}, moviePoster = "")
    }
}