package com.example.movies.presentation.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.sharp.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movies.R
import com.example.movies.domain.model.Movie
import com.example.movies.presentation.common.components.MoviesInfiniteScrollGrid
import com.example.movies.presentation.home.components.ConfirmActionAlertDialog

@Composable
fun HomeScreen(onMoviePressed: (Movie) -> Unit) {
    val viewModel: HomeViewModel = hiltViewModel()
    val context = LocalContext.current
    val uiState: HomeUiState by viewModel.uiState.collectAsState()


    Column {

        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {

            Text(
                text = stringResource(R.string.movies),
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                textAlign = TextAlign.Start,
                color = Color.Black,
                modifier = Modifier.padding(top = 24.dp, start = 16.dp)
            )

            IconButton(
                onClick = { viewModel.onEmptyPressed() }, modifier = Modifier.padding(top = 24.dp, end = 16.dp)
            ) {
                Icon(Icons.Sharp.Delete, contentDescription = stringResource(id = R.string.empty_db))
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {

            MoviesInfiniteScrollGrid(
                onMoviePressed = onMoviePressed,
                loadMoreMovies = { viewModel.getMoreMovies() },
                movies = uiState.data,
                buffer = 4
            )

            when (uiState) {
                is HomeUiState.Loading -> {
                    CircularProgressIndicator(
                        color = colorResource(id = R.color.orange),
                        modifier = Modifier
                            .padding(bottom = 32.dp)
                            .size(54.dp)
                            .align(Alignment.BottomCenter),
                        strokeWidth = 6.dp
                    )
                }

                is HomeUiState.Error -> {
                    Toast.makeText(
                        context, (uiState as HomeUiState.Error).errorMessage, Toast.LENGTH_SHORT
                    ).show()
                }

                is HomeUiState.Success -> {}

                is HomeUiState.ShowEmptyDbDialog -> {
                    ConfirmActionAlertDialog(title = stringResource(id = R.string.empty_db_dialog_title),
                        message = stringResource(id = R.string.empty_db_dialog_message),
                        icon = Icons.Default.Delete,
                        onCloseDialog = { viewModel.onCloseDialog() }) {
                        viewModel.onConfirmEmptyDatabase()
                    }
                }

                null -> {}
            }
        }
    }
}