package com.example.movies.presentation.search.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@Composable
fun Center(content: @Composable (Modifier) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.weight(0.5f))
        content(Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.weight(0.5f))
    }
}