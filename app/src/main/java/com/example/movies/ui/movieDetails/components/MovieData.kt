package com.example.movies.ui.movieDetails.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movies.R
import com.example.movies.ui.components.VoteDecimalText
import com.example.movies.ui.movieDetails.MAX_VOTE
import com.example.movies.ui.theme.MoviesTheme
import com.example.movies.utils.addDotsToLongNumber

@Composable
fun MovieData(peopleWatching: Int, genres: List<String>, vote: Float, modifier: Modifier = Modifier) {
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

    Column(modifier = modifier) {

        Text(text = peopleWatchingText)
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = genres.joinToString(separator = ", "),
            style = MaterialTheme.typography.bodySmall.copy(color = Color.DarkGray)
        )

        Spacer(modifier = Modifier.size(16.dp))

        Row {
            VoteDecimalText(
                text = vote.toString(), textStyle = MaterialTheme.typography.bodyLarge.toSpanStyle().copy(
                    color = colorResource(
                        id = R.color.orange
                    )
                )
            )

            for (i in 2 until vote.toInt() step 2) {
                Icon(Icons.Default.Star, contentDescription = null, tint = colorResource(id = R.color.orange))
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
        Surface {
            MovieData(peopleWatching = 3245, genres = listOf("Action", "Fantasy", "Adventure"), vote = 9.8f)
        }
    }
}