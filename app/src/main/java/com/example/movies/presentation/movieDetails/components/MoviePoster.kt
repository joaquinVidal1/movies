package com.example.movies.presentation.movieDetails.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.movies.R
import com.example.movies.presentation.theme.MoviesTheme

@Composable
fun MovieVideoPreview(onBackPressed: () -> Unit, moviePoster: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Image(
            painter = rememberAsyncImagePainter(
                moviePoster, placeholder = painterResource(id = R.drawable.movieplaceholder)
            ),
            contentDescription = stringResource(R.string.movie_poster),
            modifier = Modifier
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
        )

        BackButton(
            color = Color.White,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 24.dp, start = 16.dp)
        ) {
            onBackPressed()
        }

        Image(
            imageVector = Icons.Filled.PlayArrow,
            contentDescription = null,
            colorFilter = ColorFilter.tint(colorResource(id = R.color.orange)),
            modifier = Modifier
                .align(Alignment.Center)
                .size(32.dp)
        )

    }
}


@Preview
@Composable
fun MoviePosterPreview() {
    MoviesTheme {
        MovieVideoPreview(onBackPressed = {}, moviePoster = "")
    }
}