package com.example.movies.presentation.movieReviews

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.movies.R
import com.example.movies.presentation.theme.MoviesTheme

@Composable
fun MovieReview(profileImage: String?, profileName: String, review: String, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Image(
            painter = rememberAsyncImagePainter(
                profileImage,
                placeholder = rememberVectorPainter(image = Icons.Rounded.AccountCircle),
                error = rememberVectorPainter(image = Icons.Rounded.AccountCircle)
            ),
            contentDescription = stringResource(R.string.profile_image),
            modifier = Modifier
                .shadow(elevation = 2.dp, shape = RoundedCornerShape(8.dp), clip = true)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,
        )

        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(text = profileName, color = Color.Black, style = MaterialTheme.typography.titleMedium)
            Text(text = review, color = Color.Gray, style = MaterialTheme.typography.bodyMedium)
        }

    }
}

@Composable
@Preview
fun MovieReviewPreview() {
    MoviesTheme {
        Surface {
            MovieReview(
                profileImage = null,
                profileName = "Sarah Green",
                review = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
            )
        }
    }
}