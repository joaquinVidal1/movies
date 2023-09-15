package com.example.movies.presentation.common.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.example.movies.domain.model.Movie
import com.example.movies.presentation.home.components.MovieCover

@Composable
fun MoviesInfiniteScrollGrid(
    modifier: Modifier = Modifier,
    buffer: Int = 4,
    onMoviePressed: (Movie) -> Unit,
    loadMoreMovies: suspend () -> Unit,
    movies: List<Movie>
) {
    val listState = rememberLazyGridState()

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

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
        modifier = modifier,
        contentPadding = PaddingValues(top = 32.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        state = listState,
    ) {

        items(items = movies, key = { movie -> movie.id }) { movie ->
            MovieCover(movie = movie,
                modifier = Modifier
                    .size(250.dp)
                    .clickable { onMoviePressed(movie) }
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp)))
        }
    }
}