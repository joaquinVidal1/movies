package com.example.movies.presentation.main

import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.example.movies.presentation.destinations.MoviesNavigation
import com.example.movies.presentation.theme.MoviesTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class NavigateToDetailsTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: TestNavHostController
    private lateinit var context: Context

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Before
    fun setupMoodTrackerAppNavHost() {
        composeTestRule.activity.setContent {
            navController =
                TestNavHostController(ApplicationProvider.getApplicationContext())
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            MoviesTheme {
                SharedTransitionLayout {
                    MoviesNavigation(navController = navController)
                }
            }
        }
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun whenFifthMovieIsClicked_navigatesToDetailScreen() {
        // Esperar a que la lista cargue
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodes(hasTestTag("MovieItem")).fetchSemanticsNodes().size >= 5
        }

        // Obtener el quinto elemento de la lista
        val movieItemNodes = composeTestRule.onAllNodes(hasTestTag("MovieItem"))
        movieItemNodes[4].performClick() // Índice 4 porque empieza en 0

        // Obtener el texto del título de la película seleccionada
        val selectedMovieTitle = composeTestRule.onAllNodes(hasTestTag("MovieTitle"))[4]
            .fetchSemanticsNode().config.getOrNull(SemanticsProperties.Text)?.firstOrNull()?.text

        // Verificar que la pantalla de detalles está visible
        composeTestRule.onNodeWithTag("MovieDetailScreen").assertExists()

        // Verificar que el título de la película aparece en la pantalla de detalles
        composeTestRule.onNodeWithText(selectedMovieTitle ?: "").assertIsDisplayed()
    }
}