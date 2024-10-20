package com.example.movies.presentation.movieDetails.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.sharp.Favorite
import androidx.compose.material.icons.sharp.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.movies.R
import com.example.movies.presentation.common.components.shimmerBrush
import com.example.movies.presentation.theme.MoviesTheme

@Composable
fun MovieVideoPreview(
    onBackPressed: () -> Unit,
    moviePoster: String?,
    modifier: Modifier = Modifier,
    isFav: Boolean,
    onFavPressed: () -> Unit
) {
    Box(modifier = modifier) {
        AsyncImage(
            model = moviePoster,
            contentDescription = stringResource(R.string.movie_poster),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(shimmerBrush(showShimmer = moviePoster == null)),
            contentScale = ContentScale.Crop,
        )

        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 24.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth()
                .wrapContentHeight(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BackButton(
                color = Color.White, modifier = Modifier.align(CenterVertically)
            ) {
                onBackPressed()
            }

            IconButton(onClick = onFavPressed, modifier = Modifier.align(CenterVertically)) {
                val filledIcon = Icons.Sharp.Favorite
                val borderIcon = Icons.Sharp.FavoriteBorder
                Icon(
                    imageVector = if (isFav) filledIcon else borderIcon,
                    contentDescription = stringResource(id = R.string.fav_button),
                    tint = Color.White
                )
            }

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
        MovieVideoPreview(onBackPressed = {}, moviePoster = "", isFav = false, onFavPressed = {})
    }
}