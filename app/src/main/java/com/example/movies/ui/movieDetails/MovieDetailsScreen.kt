package com.example.movies.ui.movieDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movies.R
import com.example.movies.ui.components.VoteDecimalText

@Composable
fun MovieDetailsScreen(movieId: Int) {

}

@Composable
fun MovieData(peopleWatching: Int, genres: List<String>, vote: Double) {
    val peopleWatchingText = buildAnnotatedString {
        withStyle(style = MaterialTheme.typography.bodySmall.toSpanStyle().copy(fontWeight = FontWeight.Bold)){
            append(peopleWatching.toString())
        }
        withStyle(style = MaterialTheme.typography.bodySmall.toSpanStyle()){
            append(" ")
            append(stringResource(R.string.people_watching))
        }

    }
    Column {
        Text(text = peopleWatchingText)
        Text(text = genres.joinToString(separator = ", "), style = MaterialTheme.typography.bodySmall)
        VoteDecimalText(text = vote.toString())
    }

}

@Composable
@Preview
fun MovieDataPreview() {
    MovieData(peopleWatching = 3245, genres = listOf("Action", "Fantasy", "Adventure"), vote = 8.8)
}