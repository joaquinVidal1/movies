package com.example.movies.presentation.movieDetails.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.movies.domain.model.Movie
import java.time.LocalDate

@Composable
fun LoadingMovieDetails(movie: Movie) {

}

@Composable
@Preview
private fun LoadingMovieDetailsPreview() {
    LoadingMovieDetails(
        Movie(
            id = 1,
            title = "Movie Title",
            poster = "https://image.tmdb.org/t/p/w500/8Y43POKjjKDGI9MH89NW0NAzzp8.jpg",
            overview = "Movie Overview",
            releaseYear = 2024,
            voteAverage = 7.5,
        )
    )
}
