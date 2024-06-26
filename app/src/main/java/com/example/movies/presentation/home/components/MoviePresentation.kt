package com.example.movies.presentation.home.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.movies.R
import com.example.movies.domain.model.Movie
import com.example.movies.presentation.common.components.VoteDecimalText
import java.time.LocalDate


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.MovieCover(
    movie: Movie,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.sharedElement(
            state = rememberSharedContentState(key = "image/${movie.title}"),
            animatedVisibilityScope = animatedVisibilityScope,
            boundsTransform = { _, _ ->
                tween(durationMillis = 1000)
            }
        ),
        contentAlignment = Alignment.BottomStart
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                movie.poster, placeholder = painterResource(id = R.drawable.movieplaceholder)
            ),
            contentDescription = stringResource(R.string.movie_poster),
            modifier = modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 8.dp, bottom = 16.dp)
        ) {

            Text(
                text = movie.releaseDate.year.toString(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Thin,
                color = Color.White,
            )

            Text(
                text = movie.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        VoteDecimalText(
            text = movie.voteAverage.toString(),
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White).toSpanStyle(),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 4.dp, top = 4.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(colorResource(id = R.color.orange), Color.Magenta)
                    ), shape = CircleShape
                )
                .padding(2.dp)
        )
    }

}


@Composable
@Preview(showBackground = true)
fun MoviePresentationPreview() {
//    val movie = Movie(
//        id = 61565,
//        title = "Meg 2: The Trench Meg 2: The Trench Meg 2: The Trench Meg 2: The Trench",
//        overview = "An exploratory dive into the deepest depths of the ocean of a daring research team spirals into chaos when a malevolent mining operation threatens their mission and forces them into a high-stakes battle for survival.",
//        releaseDate = LocalDate.parse("2023-08-02"),
//        voteAverage = 6.9,
//        poster = "/4m1Au3YkjqsxF8iwQy0fPYSxE0h.jpg",
//    )
//    Surface(color = MaterialTheme.colorScheme.surface) {
//        MovieCover(
//            movie = movie, modifier = Modifier
//                .fillMaxSize()
//                .shadow(8.dp)
//        )
//    }
}
