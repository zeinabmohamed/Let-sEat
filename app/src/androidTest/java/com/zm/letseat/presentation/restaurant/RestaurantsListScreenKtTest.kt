package com.zm.letseat.presentation.restaurant

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.zm.letseat.domain.restaurant.entity.RestaurantSortOption
import com.zm.letseat.domain.restaurant.entity.RestaurantStatus
import com.zm.letseat.presentation.restaurant.model.RestaurantUi
import org.junit.Rule
import org.junit.Test

class RestaurantsListScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `validateLoadingStateRestaurantsListScreenContent`() {
        // Start the app
        composeTestRule.setContent {
            RestaurantsListScreenContent(Modifier, uiState = previewLoadingUiState, {}, )
        }
        composeTestRule.onNodeWithText("Restaurants list").assertIsDisplayed()
        composeTestRule.onNodeWithTag("restaurantsListTag").assertDoesNotExist()
        composeTestRule.onNodeWithText("Loading").assertIsDisplayed()
        composeTestRule.onNodeWithTag("LoadingProgressIndicator").assertIsDisplayed()
    }

    @Test
    fun `validateLoadedStateRestaurantsListScreenContent`() {
        // Start the app
        composeTestRule.setContent {
            RestaurantsListScreenContent(Modifier, uiState = previewLoadedUiState, {}, )
        }
        composeTestRule.onNodeWithText("Restaurants list").assertIsDisplayed()
        composeTestRule.onNodeWithTag("restaurantsListTag")
            .onChildren()
            .assertCountEquals(previewLoadedUiState.restaurants.size)

        composeTestRule.onNodeWithTag("restaurantsListTag")
            .onChildren()
            .onFirst()
            .assert(hasAnyChild(hasText("CLOSED")))
            .assert(hasAnyChild(hasText("Tandoori Express")))
            .assert(hasAnyChild(hasText("RATING")))
            .assert(hasAnyChild(hasText("22")))

    }

    @Test
    fun `validateEmptyStateRestaurantsListScreenContent`() {
        // Start the app
        composeTestRule.setContent {
            RestaurantsListScreenContent(Modifier, uiState = previewEmptyUiState, {}, )
        }
        composeTestRule.onNodeWithText("Restaurants list").assertIsDisplayed()
        composeTestRule.onNodeWithTag("restaurantsListTag").assertDoesNotExist()
        composeTestRule.onNodeWithText("Loading").assertDoesNotExist()
        composeTestRule.onNodeWithTag("LoadingProgressIndicator").assertDoesNotExist()
        composeTestRule.onNodeWithContentDescription("Empty List Img").assertIsDisplayed()
    }
}

private val previewLoadingUiState = RestaurantsListUiState(
    loading = true,
)

private val previewLoadedUiState = RestaurantsListUiState(
    loading = false,
    sortByOption = RestaurantSortOption.RATING,
    restaurants = listOf(
        RestaurantUi(name = "Tandoori Express",
            status = RestaurantStatus.CLOSED.toString(),
            sortingValue = "22"),
    )
)
private val previewEmptyUiState = RestaurantsListUiState(
    loading = false,
    sortByOption = RestaurantSortOption.RATING,
    restaurants = emptyList()
)
