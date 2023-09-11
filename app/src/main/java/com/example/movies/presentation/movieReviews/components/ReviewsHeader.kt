package com.example.movies.presentation.movieReviews.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.movies.R
import com.example.movies.presentation.movieDetails.components.BackButton
import com.example.movies.presentation.theme.MoviesTheme

@Composable
fun ReviewsHeader(
    onBackPressed: () -> Unit, posterPath: String, amountOfReviews: Int, modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        BackButton(
            color = Color.Black,
            onBackPressed = onBackPressed,
            modifier = modifier
                .background(color = Color.Gray.copy(alpha = 0.3f))
                .padding(vertical = 30.dp, horizontal = 8.dp)
                .fillMaxWidth()
        )

        Text(
            text = stringResource(id = R.string.reviews, amountOfReviews),
            color = Color.Gray,
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
            modifier = modifier
                .align(Alignment.BottomStart)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        )

        Image(
            painter = rememberAsyncImagePainter(
                posterPath,
                placeholder = painterResource(id = R.drawable.movieplaceholder),
                error = painterResource(id = R.drawable.movieplaceholder),
            ),
            contentDescription = stringResource(R.string.movie_poster),
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp, top = 30.dp)
                .shadow(16.dp)
                .clip(RoundedCornerShape(4.dp))
                .height(150.dp)
                .wrapContentWidth()
        )

    }
}

@Composable
@Preview
fun ReviewsHeaderPreview() {
    MoviesTheme {
        Surface {
            ReviewsHeader(onBackPressed = { }, posterPath = "", amountOfReviews = 10)
        }
    }
}