package com.example.movies.presentation.search.components

import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String,
    icon: ImageVector
) {
    OutlinedTextField(value = query,
        onValueChange = onQueryChange,
        singleLine = true,
        modifier = modifier,
        textStyle = TextStyle.Default,
        placeholder = { Text(text = hint, style = TextStyle.Default) },
        leadingIcon = { Icon(icon, contentDescription = null) })

}