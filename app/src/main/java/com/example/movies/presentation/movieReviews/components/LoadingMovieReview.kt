package com.example.movies.presentation.movieReviews.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoadingMovieReview(modifier: Modifier = Modifier) {
    Row(modifier) {
        ShimmerEffectBox(modifier = Modifier
            .size(12.dp)
            .clip(CircleShape))
        Column(
            modifier = Modifier.padding(start = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            ShimmerEffectBox(modifier = Modifier.height(16.dp).fillMaxWidth(0.33f))
            (1..6).forEach { _ ->
                ShimmerEffectBox(modifier = Modifier.height(12.dp).fillMaxWidth())
            }
        }
    }
}

@Composable
@Preview
private fun LoadingMoviwReviewPreview() {
    LoadingMovieReview()
}