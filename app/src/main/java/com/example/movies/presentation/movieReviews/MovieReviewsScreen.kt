package com.example.movies.presentation.movieReviews

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movies.presentation.movieReviews.components.LoadingMovieReview
import com.example.movies.presentation.movieReviews.components.MovieReview
import com.example.movies.presentation.movieReviews.components.ReviewsHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieReviewsScreen(onBackPressed: () -> Unit, buffer: Int = 4) {

    val viewModel: MovieReviewsViewModel = hiltViewModel()
    val uiState: MovieReviewsUiState? by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    val context = LocalContext.current

    val loadMore by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItems = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0)
            val value = (lastVisibleItemIndex > (totalItems - buffer)) && (totalItems > 1)
            value
        }
    }

    LaunchedEffect(loadMore) {
        if (loadMore) {
            viewModel.getMoreReviews()
        }
    }

    LaunchedEffect(uiState) {
        (uiState as? MovieReviewsUiState.Error)?.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.onErrorShowed()
        }
    }

    Scaffold { contentPadding ->
        Column(modifier = Modifier.padding(contentPadding)) {
            LazyColumn {
                item {
                    ReviewsHeader(
                        onBackPressed = onBackPressed,
                        posterPath = "posterPath",
                        amountOfReviews = uiState?.reviews?.amountOfReviews ?: 0,
                        modifier = Modifier.wrapContentHeight()
                    )
                }

                val itemsModifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)

                items(
                    items = uiState?.reviews?.reviews ?: listOf(),
                    key = { review -> review.id }) { review ->
                    MovieReview(
                        profileImage = review.authorDetails?.avatarPath,
                        profileName = review.authorDetails?.name ?: review.author,
                        review = review.content,
                        modifier = itemsModifier
                    )
                }

                if (uiState is MovieReviewsUiState.Loading) {
                    items(5) {
                        LoadingMovieReview(modifier = itemsModifier)
                    }
                }
            }
        }
    }
}



