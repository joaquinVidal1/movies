package com.example.movies.presentation.common.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun VoteDecimalText(text: String, textStyle: SpanStyle, modifier: Modifier = Modifier) {
    val voteValue = text.toFloat().times(10).roundToInt().div(10f)
    val parts = voteValue.toString().split(".")
    val integerPart = parts[0]
    val fractionalPart = parts[1]

    val text = buildAnnotatedString {
        withStyle(style = textStyle.copy(fontWeight = FontWeight.Bold)) {
            append(integerPart)
        }
        withStyle(
            style = textStyle.copy(
                fontWeight = FontWeight.Light, baselineShift = BaselineShift.Superscript, fontSize = 12.sp
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