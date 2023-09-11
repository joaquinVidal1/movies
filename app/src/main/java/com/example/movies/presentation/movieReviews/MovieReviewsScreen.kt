package com.example.movies.presentation.movieReviews

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MovieReviewsScreen(onBackPressed: () -> Unit, posterPath: String) {

    val viewModel: MovieReviewsViewModel = hiltViewModel()
    val uiState: MovieReviewsUiState? by viewModel.uiState.observeAsState()

    LazyColumn {
        items(items = uiState?.reviews ?: listOf(), key = { review -> review.id }
        ) { review ->
            MovieReview(
                profileImage = review.authorDetails?.avatarPath,
                profileName = review.authorDetails?.name ?: review.author,
                review = review.content,
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp)
            )
        }
    }


}