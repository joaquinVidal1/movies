package com.example.movies.presentation.movieReviews

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movies.R
import com.example.movies.presentation.home.HomeUiState

@Composable
fun MovieReviewsScreen(onBackPressed: () -> Unit, posterPath: String, buffer: Int = 2) {

    val viewModel: MovieReviewsViewModel = hiltViewModel()
    val uiState: MovieReviewsUiState? by viewModel.uiState.observeAsState()
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

    LaunchedEffect(key1 = loadMore, block = {
        if (loadMore) {
            viewModel.getMoreReviews()
        }
    })

    LazyColumn {
        val itemsModifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp)
        item() {
            Text(
                text = stringResource(id = R.string.reviews, uiState?.reviews?.amountOfReviews ?: 0),
                color = Color.Gray,
                style = MaterialTheme.typography.titleMedium,
                modifier = itemsModifier
            )
        }

        items(items = uiState?.reviews?.reviews ?: listOf(), key = { review -> review.id }) { review ->
            MovieReview(
                profileImage = review.authorDetails?.avatarPath,
                profileName = review.authorDetails?.name ?: review.author,
                review = review.content,
                modifier = itemsModifier
            )
        }
    }

    when (uiState) {
        is MovieReviewsUiState.Loading -> {
            CircularProgressIndicator(
                color = colorResource(id = R.color.orange),
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .size(54.dp),
                strokeWidth = 6.dp
            )
        }

        is MovieReviewsUiState.Error -> {
            Toast.makeText(
                context, (uiState as HomeUiState.Error).errorMessage, Toast.LENGTH_SHORT
            ).show()
        }

        is MovieReviewsUiState.Success -> {}

        null -> {}
    }
}



