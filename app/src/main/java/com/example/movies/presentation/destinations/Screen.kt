package com.example.movies.presentation.destinations

import androidx.annotation.StringRes
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.movies.R

sealed class Screen(
    val route: String,
    @StringRes val labelId: Int,
    val icon: @Composable () -> Unit,
) {
    object Home :
        Screen(route = HomeDestination.route, labelId = R.string.bottom_navigation_home, icon = {
            Icon(
                painter = painterResource(id = R.drawable.explore_icon),
                contentDescription = null,
                tint = Color.White
            )
        })

    object Favs : Screen(route = MovieReviewsDestination.FavsDestination.route,
        labelId = R.string.bottom_navigation_favs,
        icon = {
            Icon(
                imageVector = Icons.Default.Favorite, contentDescription = null, tint = Color.White
            )
        })

    object Search : Screen(route = SearchDestination.route, labelId = R.string.search, icon = {
        Icon(
            imageVector = Icons.Default.Search, contentDescription = null, tint = Color.White
        )
    })
}
