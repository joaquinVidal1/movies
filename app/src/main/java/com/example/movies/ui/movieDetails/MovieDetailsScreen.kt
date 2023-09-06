package com.example.movies.ui.movieDetails

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.movies.R
import com.example.movies.ui.movieDetails.components.MovieData
import com.example.movies.ui.movieDetails.components.MoviePoster

@Composable
fun MovieDetailsScreen(onBackPressed: () -> Unit) {

    val viewModel: MovieDetailsViewModel = hiltViewModel()
    val movie by viewModel.movie.collectAsState(initial = null)
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.systemMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    Box {
        movie?.let { movie ->
            MoviePoster(
                onBackPressed = onBackPressed,
                moviePoster = movie.videoPreviewPath,
                modifier = Modifier
                    .fillMaxHeight(0.4f)
                    .align(
                        Alignment.TopStart
                    )
            )

            Row(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        movie.posterPath, placeholder = painterResource(id = R.drawable.movieplaceholder)
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
                    verticalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = movie.title,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth()
                    )

//                    Spacer(modifier = Modifier.size(32.dp))

                    MovieData(
                        peopleWatching = movie.peopleWatching,
                        genres = movie.genres,
                        vote = movie.voteAverage,
                        modifier = Modifier.wrapContentHeight()
                    )

                }
            }
        }
    }

}


const val MAX_VOTE = 10