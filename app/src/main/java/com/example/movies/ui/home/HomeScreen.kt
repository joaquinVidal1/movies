package com.example.movies.ui.home

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movies.R
import com.example.movies.model.Movie
import com.example.movies.ui.home.components.MovieCover
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

@Composable
fun HomeScreen(onMoviePressed: (Movie) -> Unit) {
    val viewModel: HomeViewModel = hiltViewModel()
    val movies by viewModel.movies.collectAsState(initial = emptyList())
    val context = LocalContext.current

    LaunchedEffect(key1 = LocalDate.now().dayOfMonth, key2 = LocalDate.now().month, block = {
        withContext(Dispatchers.IO) {
            viewModel.updateMovies()
        }
    })

    LaunchedEffect(Unit) {
        viewModel.systemMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item(span = { GridItemSpan(maxCurrentLineSpan) }, content = {
            Text(
                text = stringResource(R.string.movies),
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                textAlign = TextAlign.Start,
                color = Color.Black,
                modifier = Modifier.padding(top = 32.dp),
            )
        })

        items(items = movies, key = { movie -> movie.id }) { movie ->
            MovieCover(movie = movie,
                modifier = Modifier
                    .size(250.dp)
                    .clickable { onMoviePressed(movie) }
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp))
            )
        }

    }
}