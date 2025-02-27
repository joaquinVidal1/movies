package com.example.movies.presentation.main

import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigateToDetailsTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun navigateToDetails() {
        composeTestRule.setContent {
            MoviesApp()
        }

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodes(hasTestTag("MovieItem")).fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText(movieTitle).performClick()

        composeTestRule.onNodeWithTag("MovieDetailScreen").assertExists()
        composeTestRule.onNodeWithText(movieTitle).assertIsDisplayed()
    }
}