package com.example.movies.presentation.movieDetails.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.example.movies.domain.utils.addDotsToLongNumber
import com.example.movies.presentation.common.components.VoteDecimalText
import com.example.movies.presentation.movieDetails.MAX_VOTE
import com.example.movies.presentation.movieReviews.components.ShimmerEffectBox
import com.example.movies.presentation.theme.MoviesTheme

@Composable
fun MovieData(
    peopleWatching: Int?, genres: List<String>?, vote: Float, modifier: Modifier = Modifier
) {
    val peopleWatchingText = if (peopleWatching != null) buildAnnotatedString {
        withStyle(
            style = MaterialTheme.typography.bodySmall.toSpanStyle()
                .copy(fontWeight = FontWeight.Bold, color = Color.Black)
        ) {
            append(peopleWatching.toString().addDotsToLongNumber())
        }
        withStyle(
            style = MaterialTheme.typography.bodySmall.toSpanStyle().copy(color = Color.DarkGray)
        ) {
            append(" ")
            append(stringResource(R.string.people_watching))
        }
    } else null

    Column(modifier = modifier) {

        peopleWatchingText?.let { Text(text = it) } ?: ShimmerEffectBox(
            modifier = Modifier.height(12.dp).width(80.dp)
        )

        Spacer(modifier = Modifier.size(4.dp))

        genres?.let {
            Text(
                text = it.joinToString(separator = ", "),
                style = MaterialTheme.typography.bodySmall.copy(color = Color.DarkGray)
            )
        } ?: ShimmerEffectBox(
            modifier = Modifier.height(12.dp).width(100.dp)
        )

        Spacer(modifier = Modifier.size(16.dp))

        Row {
            VoteDecimalText(
                text = vote.toString(),
                textStyle = MaterialTheme.typography.bodyLarge.toSpanStyle().copy(
                    color = colorResource(
                        id = R.color.orange
                    )
                )
            )

            for (i in 2 until vote.toInt() step 2) {
                Icon(
                    Icons.Default.Star,
                    contentDescription = null,
                    tint = colorResource(id = R.color.orange)
                )
            }

            for (i in vote.toInt() until MAX_VOTE step 2) {
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
            MovieData(
                peopleWatching = 3245,
                genres = listOf("Action", "Fantasy", "Adventure"),
                vote = 9.8f
            )
        }
    }
}
