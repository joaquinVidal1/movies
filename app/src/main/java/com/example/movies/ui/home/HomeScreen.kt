package com.example.movies.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movies.model.Movie
import com.example.movies.ui.home.components.MovieCover
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

@Composable
fun HomeScreen(onMoviePressed: (Movie) -> Unit) {
    val viewModel: HomeViewModel = hiltViewModel()
    val movies by viewModel.movies.collectAsState(initial = emptyList())

    LaunchedEffect(key1 = LocalDate.now().dayOfMonth, key2 = LocalDate.now().month, block = {
        withContext(Dispatchers.IO) {
            viewModel.updateMovies()
        }
    })

    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 100.dp), modifier = Modifier.fillMaxSize()) {
        items(items = movies) { movie ->
            MovieCover(movie = movie, modifier = Modifier.clickable { onMoviePressed(movie) })
        }

    }
}