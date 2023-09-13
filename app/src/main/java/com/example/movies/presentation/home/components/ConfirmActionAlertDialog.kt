package com.example.movies.presentation.home.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.DialogProperties
import com.example.movies.R

@Composable
fun ConfirmActionAlertDialog(
    title: String,
    message: String,
    icon: ImageVector,
    onCloseDialog: () -> Unit,
    onActionConfirmed: () -> Unit,
) {

    AlertDialog(
        onDismissRequest = onCloseDialog,
        confirmButton = {

            TextButton(onClick = onActionConfirmed) {
                Text(text = stringResource(id = R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onCloseDialog) {
                Text(text = stringResource(id = R.string.cancel))
            }
        },
        title = { Text(text = title) },
        text = { Text(text = message, textAlign = TextAlign.Center) },
        icon = {
            Icon(icon, contentDescription = null, tint = Color.White)
        },
        containerColor = Color.DarkGray,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    )
}