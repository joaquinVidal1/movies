package com.example.movies.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movies.R
import com.example.movies.domain.model.Movie
import com.example.movies.presentation.common.components.MoviesInfiniteScrollGrid
import com.example.movies.presentation.search.components.Center
import com.example.movies.presentation.search.components.SearchBar

@Composable
fun SearchScreen(onMoviePressed: (Movie) -> Unit) {

    val viewModel: SearchViewModel = hiltViewModel()
    val uiState: SearchUiState by viewModel.uiState.collectAsState()

    uiState.let {
        Column {
            SearchBar(
                query = it.queryTitle,
                onQueryChange = { newQuery -> viewModel.search(queryTitle = newQuery) },
                hint = stringResource(R.string.title),
                icon = Icons.Rounded.Search,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 16.dp)
            )

            when (it) {
                is SearchUiState.Success -> {
                    if (it.data.isEmpty()) {
                        Center { modifier ->
                            Text(
                                text = stringResource(R.string.search_movies),
                                style = TextStyle.Default,
                                fontSize = 24.sp,
                                modifier = modifier
                            )
                        }
                    } else {
                        MoviesInfiniteScrollGrid(
                            onMoviePressed = { onMoviePressed(it) },
                            loadMoreMovies = { viewModel.loadMoreMovies() },
                            movies = it.data
                        )
                    }
                }

                is SearchUiState.Error -> {
                    Center { modifier ->
                        Text(
                            text = it.errorMessage, modifier = modifier, style = TextStyle.Default
                        )

                    }
                }

                is SearchUiState.Loading -> {
                    Center { modifier ->
                        CircularProgressIndicator(
                            color = colorResource(id = R.color.orange),
                            modifier = modifier
                                .padding(bottom = 32.dp)
                                .size(54.dp),
                            strokeWidth = 6.dp
                        )
                    }
                }
            }
        }
    }
}