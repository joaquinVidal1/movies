package com.example.movies.presentation.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movies.R
import com.example.movies.domain.model.Movie
import com.example.movies.presentation.home.components.MovieCover
import com.example.movies.presentation.search.components.SearchBar

@Composable
fun SearchScreen(onMoviePressed: (Movie) -> Unit) {

    val viewModel: SearchViewModel = hiltViewModel()
    val uiState: SearchUiState by viewModel.uiState.collectAsState()

    uiState.let {
        Column {
            SearchBar(
                query = it.queryTitle,
                onQueryChange = { newQuery ->
                    viewModel.search(
                        queryTitle = newQuery, queryOverview = uiState.queryOverview
                    )
                },
                hint = "Title",
                icon = Icons.Rounded.Search,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 16.dp)
            )
            SearchBar(
                query = it.queryOverview,
                onQueryChange = { newQuery ->
                    viewModel.search(
                        queryOverview = newQuery, queryTitle = uiState.queryTitle
                    )
                },
                hint = "Overview",
                icon = Icons.Rounded.Search,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 16.dp)
            )

            when (it) {
                is SearchUiState.Success -> {
                    if (it.data.isEmpty()) {
                        Text(
                            text = "Busque peliculas", style = TextStyle.Default
                        )
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(minSize = 150.dp),
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(
                                top = 32.dp, start = 16.dp, end = 16.dp, bottom = 24.dp
                            ),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                        ) {

                            items(items = it.data, key = { movie -> movie.id }) { movie ->
                                MovieCover(
                                    movie = movie,
                                    modifier = Modifier
                                        .size(250.dp)
                                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(8.dp))
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable { onMoviePressed(movie) }
                                )
                            }
                        }
                    }
                }

                is SearchUiState.Error -> {
                    Column {
                        Text(
                            text = it.errorMessage,
                            modifier = Modifier.weight(0.5F),
                            style = TextStyle.Default
                        )
                    }
                }

                is SearchUiState.Loading -> {
                    CircularProgressIndicator(
                        color = colorResource(id = R.color.orange),
                        modifier = Modifier
                            .padding(bottom = 32.dp)
                            .size(54.dp),
                        strokeWidth = 6.dp
                    )
                }
            }
        }
    }
}