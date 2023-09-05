package com.example.movies.ui.movieDetails

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
import com.example.movies.ui.theme.MoviesTheme
import com.example.movies.utils.addDotsToLongNumber
import kotlin.math.roundToInt

@Composable
fun MovieDetailsScreen(movieId: Int) {

}

@Composable
fun MovieData(peopleWatching: Int, genres: List<String>, vote: Double) {
    val peopleWatchingText = buildAnnotatedString {
        withStyle(
            style = MaterialTheme.typography.bodySmall.toSpanStyle()
                .copy(fontWeight = FontWeight.Bold, color = Color.Black)
        ) {
            append(peopleWatching.toString().addDotsToLongNumber())
        }
        withStyle(style = MaterialTheme.typography.bodySmall.toSpanStyle().copy(color = Color.DarkGray)) {
            append(" ")
            append(stringResource(R.string.people_watching))
        }

    }
    Column {
        Text(text = peopleWatchingText)
        Text(
            text = genres.joinToString(separator = ", "),
            style = MaterialTheme.typography.bodySmall.copy(color = Color.DarkGray)
        )
        Row {
            VoteDecimalText(
                text = vote.toString(), textStyle = MaterialTheme.typography.bodyLarge.toSpanStyle().copy(
                    color = colorResource(
                        id = R.color.orange
                    )
                )
            )

            for (i in 0 until vote.toInt() step 2) {
                Icon(Icons.Default.Star, contentDescription = null)
            }

            for (i in vote.toInt()until MAX_VOTE step 2){
                Icon(Icons.Default.Star, contentDescription = null, tint = Color.Gray)
            }
        }
    }

}

@Composable
@Preview
fun MovieDataPreview() {
    MoviesTheme {
        MovieData(peopleWatching = 3245, genres = listOf("Action", "Fantasy", "Adventure"), vote = 8.8)
    }
}

const val MAX_VOTE = 10