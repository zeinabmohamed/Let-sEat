package com.zm.letseat.presentation.restaurant

import android.widget.HorizontalScrollView
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Card
import androidx.compose.material.Chip
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zm.letseat.R
import com.zm.letseat.domain.restaurant.entity.RestaurantSortOption
import com.zm.letseat.domain.restaurant.entity.RestaurantStatus
import com.zm.letseat.presentation.restaurant.model.RestaurantUi


@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2, heightDp = 300)
@Composable
fun RestaurantsListScreenPreview() {
    val modifier = Modifier
    val uiState = previewLoadedUiState
    RestaurantsListScreenContent(modifier, uiState) {}
}

@Composable
fun RestaurantsListScreen(viewModel: RestaurantsListViewModel = viewModel()) {
    val modifier = Modifier
    val uiState = viewModel.uiState
    RestaurantsListScreenContent(modifier, uiState) {
        viewModel.loadRestaurantsList(it)
    }
}

@Composable
fun RestaurantsListScreenContent(
    modifier: Modifier.Companion,
    uiState: RestaurantsListUiState,
    onSortOptionChanged: (RestaurantSortOption) -> Unit,
) {
    Scaffold(
        topBar = {
            RestaurantsListTopAppBar(modifier, onSortOptionChanged)
        },
        backgroundColor = MaterialTheme.colors.surface
    ) { padding ->
        RestaurantsList(modifier.padding(padding), uiState.sortByOption, uiState.restaurants)
    }
}

@Composable
private fun RestaurantsListTopAppBar(
    modifier: Modifier = Modifier,
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
            SortingOptionsDropDownMenu(onSortOptionChanged)
        },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.White,
    )
}

@Composable
private fun SortingOptionsDropDownMenu(
    onSortOptionChanged: (RestaurantSortOption) -> Unit,
) {
    val expanded = remember { mutableStateOf(false) }
    Box(
        Modifier
            .wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = {
            expanded.value = expanded.value.not()
        }) {
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
            RestaurantSortOption.values().forEach { sortOption ->
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

@Composable
private fun RestaurantsList(
    modifier: Modifier = Modifier,
    sortingOption: RestaurantSortOption,
    restaurantList: List<RestaurantUi>,
) {
    LazyColumn(modifier = modifier.testTag("restaurantsListTag").fillMaxSize()) {
        items(restaurantList) { restaurantItem ->
            RestaurantItem(
                modifier = modifier,
                sortingOption = sortingOption,
                restaurantUi = restaurantItem
            )
        }
    }
}

/**
 * The restaurant item should contain :
 * the name of the restaurants,
 * the current opening state,
 * the selected sorting and the sort value for a restaurant.
 */
@Composable
private fun RestaurantItem(
    modifier: Modifier = Modifier,
    sortingOption: RestaurantSortOption = RestaurantSortOption.RATING,
    restaurantUi: RestaurantUi,
) {
    Card(modifier = modifier.padding(8.dp), elevation = 8.dp) {
        Column(modifier
            .fillMaxWidth()
            .padding(8.dp)) {
            Text(text = restaurantUi.status,
                style = MaterialTheme.typography.overline)
            Text(text = restaurantUi.name,
                modifier.fillMaxWidth(1f), maxLines = 1,
                style = MaterialTheme.typography.h5)
            Spacer(modifier = modifier.padding(bottom = 8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = sortingOption.toString(),
                    modifier = modifier.weight(1f),
                    style = MaterialTheme.typography.body2,

                    )
                Text(text = restaurantUi.sortingValue,
                    modifier = modifier.weight(1f),
                    style = MaterialTheme.typography.body2)
            }
        }
    }
}

private val previewLoadedUiState = RestaurantsListUiState(
    loading = false,
    sortByOption = RestaurantSortOption.RATING,
    restaurants = listOf(
        RestaurantUi(name = "Tandoori Express",
            status = RestaurantStatus.CLOSED.toString(),
            sortingValue = "22"),
        RestaurantUi(name = "Tandoori Express2",
            status = RestaurantStatus.CLOSED.toString(),
            sortingValue = "22"),
        RestaurantUi(name = "Tandoori Express2",
            status = RestaurantStatus.CLOSED.toString(),
            sortingValue = "22"),
        RestaurantUi(name = "Tandoori Express2",
            status = RestaurantStatus.CLOSED.toString(),
            sortingValue = "22"),
        RestaurantUi(name = "Tandoori Express2",
            status = RestaurantStatus.CLOSED.toString(),
            sortingValue = "22"), RestaurantUi(name = "Tandoori Express2",
            status = RestaurantStatus.CLOSED.toString(),
            sortingValue = "22")
    )
)
