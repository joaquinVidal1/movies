package com.example.movies.ui.movieDetails.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import coil.compose.rememberAsyncImagePainter
import com.example.movies.R
import com.example.movies.ui.theme.MoviesTheme

@Composable
fun MovieDetails(
    title: String,
    peopleWatching: Int,
    genres: List<String>,
    voteAverage: Float,
    posterPath: String,
    videoPreviewPath: String,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
    ) {
        MoviePoster(
            onBackPressed = onBackPressed,
            moviePoster = videoPreviewPath,
            modifier = Modifier
                .align(
                    Alignment.TopStart
                )
                .background(Color.Gray)
        )

        Row(
            modifier = Modifier
                .offset(y = 150.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    posterPath, placeholder = painterResource(id = R.drawable.movieplaceholder)
                ),
                modifier = Modifier
                    .padding(start = 24.dp)
                    .shadow(8.dp)
                    .height(200.dp),
                contentDescription = stringResource(R.string.movie_poster),
                contentScale = ContentScale.FillHeight,
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .height(200.dp),
                horizontalAlignment = Alignment.Start,
            ) {

                Spacer(modifier = Modifier.size(8.dp))

                Text(
                    text = title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.size(48.dp))

                MovieData(
                    peopleWatching = peopleWatching,
                    genres = genres,
                    vote = voteAverage,
                    modifier = Modifier.wrapContentHeight()
                )

                Spacer(modifier = Modifier.size(8.dp))
            }
        }

    }
}

@Preview
@Composable
fun MovieDetailsPreview() {
    MoviesTheme {
        MovieDetails(title = "Justice League",
            peopleWatching = 3292,
            genres = listOf("Action", "Adventure", "Fantast"),
            voteAverage = 9.8f,
            posterPath = "",
            videoPreviewPath = "",
            onBackPressed = {})
    }
}