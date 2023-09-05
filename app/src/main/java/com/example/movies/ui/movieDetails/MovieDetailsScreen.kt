package com.example.movies.ui.movieDetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.movies.R
import com.example.movies.ui.components.VoteDecimalText
import com.example.movies.ui.movieDetails.components.MoviePoster
import com.example.movies.ui.theme.MoviesTheme
import com.example.movies.utils.addDotsToLongNumber
import kotlin.math.roundToInt

@Composable
fun MovieDetailsScreen(movieId: Int, onBackPressed: () -> Unit) {
    Column {
        MoviePoster(onBackPressed = onBackPressed, moviePoster = )
    }

}


const val MAX_VOTE = 10