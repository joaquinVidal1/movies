package com.example.movies.presentation.fav

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movies.R
import com.example.movies.domain.model.Movie
import com.example.movies.presentation.common.components.MoviesInfiniteScrollGrid
import com.example.movies.presentation.home.components.MovieCover
import com.example.movies.presentation.search.components.Center

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.FavScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    onMoviePressed: (Movie) -> Unit
) {

    val viewModel: FavsViewModel = hiltViewModel()
//    val listState = rememberLazyGridState()
    val uiState by viewModel.uiState.collectAsState()

//    LazyVerticalGrid(
//        columns = GridCells.Adaptive(minSize = 150.dp),
//        modifier = Modifier.fillMaxSize(),
//        contentPadding = PaddingValues(top = 32.dp, start = 16.dp, end = 16.dp),
//        verticalArrangement = Arrangement.spacedBy(16.dp),
//        horizontalArrangement = Arrangement.spacedBy(16.dp),
//        state = listState,
//    ) {
//
//        items(items = uiState.data, key = { movie -> movie.id }) { movie ->
//            MovieCover(movie = movie,
//                modifier = Modifier
//                    .size(250.dp)
//                    .clickable { onMoviePressed(movie) }
//                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(8.dp))
//                    .clip(RoundedCornerShape(8.dp)))
//        }
//    }

    if (uiState.data.isNotEmpty()) {
        MoviesInfiniteScrollGrid(
            onMoviePressed = onMoviePressed,
            loadMoreMovies = null,
            movies = uiState.data,
            buffer = 4,
            animatedVisibilityScope = animatedVisibilityScope,
            transitionKey = "Favs"
        )
    } else {
        Center {
            Text(text = stringResource(id = R.string.favs_empty))
        }
    }

}