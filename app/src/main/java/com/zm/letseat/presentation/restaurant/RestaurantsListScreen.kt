package com.zm.letseat.presentation.restaurant

import RestaurantsListEmpty
import RestaurantsListLoading
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zm.letseat.R
import com.zm.letseat.domain.restaurant.entity.RestaurantSortOption
import com.zm.letseat.domain.restaurant.entity.RestaurantSortOption.*


@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2, heightDp = 500)
@Composable
fun RestaurantsListScreenPreview() {
    val modifier = Modifier
    val uiState = previewLoadedUiState
    RestaurantsListScreenContent(modifier, uiState, {}, "", {})
}

@Composable
fun RestaurantsListScreen(viewModel: RestaurantsListViewModel = viewModel()) {
    val modifier = Modifier
    val uiState = viewModel.uiState
    val lastSelectedSortOption = remember {
        mutableStateOf<RestaurantSortOption?>(null)
    }
    val searchTextState = remember { mutableStateOf("") }

    RestaurantsListScreenContent(
        modifier, uiState,
        {
            lastSelectedSortOption.value = it
            viewModel.loadRestaurantsList(it)
        }, searchTextState.value, { searchText ->
            searchTextState.value = searchText
            viewModel.filterRestaurant(
                searchText
            )
        }
    )
}

@Composable
fun RestaurantsListScreenContent(
    modifier: Modifier.Companion,
    uiState: RestaurantsListUiState,
    onSortOptionChanged: (RestaurantSortOption) -> Unit,
    searchText: String,
    onValueChanged: (String) -> Unit,
) {
    val sortingActionEnabled = (
            uiState.loading || uiState.restaurants.isEmpty() || uiState.filter
            ).not()
    Scaffold(
        topBar = {
            RestaurantsListTopAppBar(modifier, sortingActionEnabled, onSortOptionChanged)
        },
        backgroundColor = MaterialTheme.colors.surface
    ) { padding ->
        if (uiState.loading) {
            RestaurantsListLoading()
        } else {
            if (uiState.filter.not() && uiState.restaurants.isEmpty()) {
                RestaurantsListEmpty()
            } else {
                RestaurantsList(
                    modifier = modifier.padding(padding),
                    sortingOption = uiState.sortByOption,
                    restaurantList = uiState.restaurants,
                    searchText = searchText,
                    onValueChanged = onValueChanged
                )
            }
        }
    }
}

@Composable
private fun RestaurantsListTopAppBar(
    modifier: Modifier = Modifier,
    sortingActionEnabled: Boolean = true,
    onSortOptionChanged: (RestaurantSortOption) -> Unit,
) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.fillMaxWidth(1f)) {
                Text(text = stringResource(R.string.restaurants_list))
            }
        },
        actions = {
            SortingOptionsDropDownMenu(sortingActionEnabled, onSortOptionChanged)
        },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.White,
    )
}

@Composable
private fun SortingOptionsDropDownMenu(
    sortingActionEnabled: Boolean,
    onSortOptionChanged: (RestaurantSortOption) -> Unit,
) {
    val expanded = remember { mutableStateOf(false) }
    Box(
        Modifier
            .wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = {
            expanded.value = expanded.value.not()
        }, enabled = sortingActionEnabled) {
            Icon(
                painter = painterResource(id = R.drawable.swap_vert_black_24dp),
                contentDescription = null
            )
        }
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier.background(MaterialTheme.colors.surface)
        ) {
            values().forEach { sortOption ->
                DropdownMenuItem(onClick = {
                    expanded.value = false
                    onSortOptionChanged(sortOption)
                }) {
                    Text(text = sortOption.toString())
                }
            }
        }
    }
}
