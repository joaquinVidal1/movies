package com.example.movies.presentation.movieDetails.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.movies.R
import com.example.movies.presentation.theme.MoviesTheme

@Composable
fun BackButton(modifier: Modifier = Modifier, color: Color, onBackPressed: () -> Unit) {
    Row(modifier = modifier.clickable { onBackPressed() }) {
        Icon(
            painter = painterResource(id = R.drawable.chevron_left), contentDescription = null, tint = color
        )
        Text(
            text = stringResource(R.string.back), style = MaterialTheme.typography.bodyLarge, color = color
        )
    }
}

@Composable
@Preview
fun BackButtonPreview() {
    MoviesTheme {
        BackButton(color = Color.White) {}
    }
}