package com.example.movies.presentation.movieDetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.movies.R


@Composable
fun GradientFloatingActionButton(
    gradientColors: List<Color>,
    elevation: Dp = 0.dp,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable() (BoxScope.() -> Unit),
) {
    val shape = RoundedCornerShape(30.dp)
    Surface(modifier = modifier
        .shadow(elevation, shape)
        .clip(shape)
        .background(Brush.linearGradient(gradientColors))
        .clickable { onClick() }
        .wrapContentSize(),
        color = Color.Transparent,
        shape = shape,
        tonalElevation = elevation,
        content = {
            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(vertical = 16.dp, horizontal = 32.dp),
                contentAlignment = Alignment.Center,
                content = content
            )

        })
}

@Preview
@Composable
fun GradientFabPreview(onShowReviewsPressed: () -> Unit = {}) {
    GradientFloatingActionButton(
        gradientColors = listOf(colorResource(id = R.color.orange), Color.Magenta), onClick = onShowReviewsPressed
    ) {
        Text(
            text = stringResource(id = R.string.show_reviews),
            maxLines = 1,
            style = MaterialTheme.typography.titleMedium,
            color = Color.White
        )
    }
}
