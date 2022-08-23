package com.zm.letseat.presentation.restaurant

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zm.letseat.R
import com.zm.letseat.domain.restaurant.entity.RestaurantSortOption
import com.zm.letseat.domain.restaurant.entity.RestaurantStatus
import com.zm.letseat.presentation.restaurant.model.RestaurantUi


@Preview
@Composable
private fun RestaurantsListTopAppBar(
    modifier: Modifier = Modifier,
    sortingOption: RestaurantSortOption = RestaurantSortOption.RATING,
) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.fillMaxWidth(1f)) {
                Text(text = stringResource(R.string.restaurants_list))
            }
        },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.White,
    )
}

@Preview
@Composable
private fun RestaurantsList(
    modifier: Modifier = Modifier,
    sortingOption: RestaurantSortOption = RestaurantSortOption.RATING,
    restaurantList: List<RestaurantUi> = listOf(
        RestaurantUi(name = "Tandoori Express",
            status = RestaurantStatus.CLOSED.toString(),
            sortingValue = "22"),
        RestaurantUi(name = "Tandoori Express2",
            status = RestaurantStatus.CLOSED.toString(),
            sortingValue = "22")
    ),
) {
    LazyColumn {
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
    Card(modifier = modifier.padding(8.dp)) {
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