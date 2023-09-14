package com.example.movies.presentation.common.components

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.movies.R
import com.example.movies.domain.model.Movie
import com.example.movies.presentation.home.HomeUiState
import com.example.movies.presentation.home.components.ConfirmActionAlertDialog
import com.example.movies.presentation.home.components.MovieCover

@Composable
fun MoviesInfiniteScrollGrid(
    modifier: Modifier = Modifier,
    listState: LazyGridState,
    buffer: Int,
    onMoviePressed: (Movie) -> Unit,
    loadMoreMovies: () -> Unit,
    uiState: HomeUiState
) {

    val loadMore by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItems = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0)
            val value = (lastVisibleItemIndex > (totalItems - buffer)) && (totalItems > 1)
            value
        }
    }

    LaunchedEffect(key1 = loadMore, block = {
        if (loadMore) {
            loadMoreMovies()
        }
    })

    Box(modifier = Modifier.fillMaxSize()) {

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 150.dp),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 32.dp, start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            state = listState,
        ) {

            items(items = uiState.data, key = { movie -> movie.id }) { movie ->
                MovieCover(movie = movie,
                    modifier = Modifier
                        .size(250.dp)
                        .clickable { onMoviePressed(movie) }
                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(8.dp))
                        .clip(RoundedCornerShape(8.dp)))
            }
        }

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
                    context, uiState.errorMessage, Toast.LENGTH_SHORT
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