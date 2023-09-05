package com.example.movies.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

@Composable
fun VoteDecimalText(text: String, modifier: Modifier = Modifier) {
    val parts = text.split(".")
    val integerPart = parts[0]
    val fractionalPart = if (parts.size > 1) parts[1] else "0"

    val text = buildAnnotatedString {
        withStyle(style = SpanStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)) {
            append(integerPart)
        }
        withStyle(
            style = SpanStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.Light,
                baselineShift = BaselineShift.Superscript,
                color = Color.White
            )
        ) {
            append(".$fractionalPart")
        }
    }
    Box(
        modifier = modifier, contentAlignment = Alignment.Center
    ) {
        Text(
            text = text, textAlign = TextAlign.End
        )
    }
}