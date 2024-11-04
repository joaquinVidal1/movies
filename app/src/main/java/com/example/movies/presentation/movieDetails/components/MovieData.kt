package com.example.movies.presentation.movieDetails.components

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import coil.compose.rememberAsyncImagePainter
import com.example.movies.R
import com.example.movies.data.network.model.WatchProvider
import com.example.movies.domain.utils.addDotsToLongNumber
import com.example.movies.presentation.common.components.VoteDecimalText
import com.example.movies.presentation.movieDetails.MAX_VOTE
import com.example.movies.presentation.theme.MoviesTheme
import java.time.Duration
import java.time.LocalDate

@Composable
fun MovieData(
    peopleWatching: Int,
    genres: List<String>,
    releaseDate: LocalDate,
    duration: Duration,
    budget: Long,
    revenue: Long,
    vote: Float,
    watchProviders: List<WatchProvider>,
    modifier: Modifier = Modifier
) {
    val peopleWatchingText = buildAnnotatedString {
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

    }

    Column(modifier = modifier) {

        Text(text = peopleWatchingText)

        Spacer(modifier = Modifier.size(4.dp))

        Text(
            text = genres.joinToString(separator = ", "),
            style = MaterialTheme.typography.bodySmall.copy(color = Color.DarkGray)
        )

        Text(
            text = stringResource(id = R.string.release_date, releaseDate.toString()),
            style = MaterialTheme.typography.bodySmall.copy(color = Color.DarkGray)
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && duration.toHoursPart() > 0) {
            Text(
                text = stringResource(
                    id = R.string.duration, duration.toHoursPart(), duration.toMinutesPart()
                ), style = MaterialTheme.typography.bodySmall.copy(color = Color.DarkGray)
            )
        } else {
            Text(
                text = stringResource(
                    id = R.string.duration_mins, duration.toMinutes()
                ), style = MaterialTheme.typography.bodySmall.copy(color = Color.DarkGray)
            )
        }
        Text(
            text = stringResource(id = R.string.budget, budget),
            style = MaterialTheme.typography.bodySmall.copy(color = Color.DarkGray)
        )
        Text(
            text = stringResource(id = R.string.revenue, revenue),
            style = MaterialTheme.typography.bodySmall.copy(color = Color.DarkGray)
        )

        Spacer(modifier = Modifier.size(16.dp))

        LazyRow(
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth()
        ) {
            items(watchProviders) {
                Image(
                    painter = rememberAsyncImagePainter(it.logoPath),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .padding(horizontal = 4.dp)
                )
            }
        }

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
                genres = listOf("Action", "Fantasy", "Adventure", "Action", ""),
                vote = 9.8f,
                watchProviders = listOf(),
                releaseDate = LocalDate.now(),
                duration = Duration.ofMinutes(30),
                budget = 1341,
                revenue = 35153,
            )
        }
    }
}