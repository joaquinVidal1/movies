package com.example.movies.presentation.movieReviews

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MovieReviewsScreen(onBackPressed: () -> Unit, posterPath: String) {

    val viewModel: MovieReviewsViewModel = hiltViewModel()
    val uiState: MovieReviewsUiState? by viewModel.uiState.observeAsState()

    LazyColumn {
        items(items = uiState?.reviews ?: listOf(), key = { review -> review }) { movie ->
            MovieReview(
                profileImage = movie.authorDetails.avatarPath,
                profileName = movie.authorDetails.name,
                review = movie.content
            )
        }
    }


}