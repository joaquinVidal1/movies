package com.example.movies.presentation.destinations

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.movies.R

sealed class Screen(val route: String, @StringRes val labelId: Int, val icon: ImageVector) {
    object Home :
        Screen(route = HomeDestination.route, labelId = R.string.bottom_navigation_home, icon = Icons.Default.Home)

    object Favs : Screen(
        route = MovieReviewsDestination.FavsDestination.route,
        labelId = R.string.bottom_navigation_favs,
        icon = Icons.Default.Favorite
    )

    object Search: Screen(
        route = SearchDestination.route,
        labelId = R.string.search,
        icon = Icons.Default.Search
    )
}
