package com.example.movies.ui.home.components

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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.movies.R
import com.example.movies.model.Movie
import java.time.LocalDate


@Composable
fun MovieCover(movie: Movie, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier, contentAlignment = Alignment.BottomStart
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                movie.poster, placeholder = painterResource(id = R.drawable.movieplaceholder)
            ),
            contentDescription = stringResource(R.string.movie_poster),
            modifier = Modifier.fillMaxSize(),
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
                text = movie.title, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White
            )
        }
        DecimalText(
            text = movie.voteAverage.toString(),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 4.dp, top = 4.dp)
        )
    }

}


@Composable
@Preview(showBackground = true)
fun MoviePresentationPreview() {
    val movie = Movie(
        id = "615656",
        title = "Meg 2: The Trench Meg 2: The Trench Meg 2: The Trench Meg 2: The Trench",
        overview = "An exploratory dive into the deepest depths of the ocean of a daring research team spirals into chaos when a malevolent mining operation threatens their mission and forces them into a high-stakes battle for survival.",
        releaseDate = LocalDate.parse("2023-08-02"),
        voteAverage = 6.9,
        poster = "/4m1Au3YkjqsxF8iwQy0fPYSxE0h.jpg",
        savedTimeStamp = System.currentTimeMillis()
    )
    Surface(color = MaterialTheme.colorScheme.surface) {
        MovieCover(
            movie = movie, modifier = Modifier
                .fillMaxSize()
                .shadow(8.dp)
        )
    }
}

@Composable
fun DecimalText(text: String, modifier: Modifier = Modifier) {
    val parts = text.split(".")
    val integerPart = parts[0]
    val fractionalPart = if (parts.size > 1) parts[1] else "0"

    val text = buildAnnotatedString {
        withStyle(style = SpanStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)) {
            append(integerPart)
        }
        withStyle(
            style = SpanStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.Light,
                baselineShift = BaselineShift.Superscript,
                color = Color.White
            )
        ) {
            append(".$fractionalPart")
        }
    }
    Box(
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(colorResource(id = R.color.orange), Color.Magenta)
                ), shape = CircleShape
            )
            .padding(2.dp), contentAlignment = Alignment.Center


    ) {
        Text(
            text = text, textAlign = TextAlign.End
        )
    }
}
