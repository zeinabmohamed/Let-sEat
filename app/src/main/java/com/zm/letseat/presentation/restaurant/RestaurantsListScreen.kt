package com.zm.letseat.presentation.restaurant

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zm.letseat.domain.restaurant.entity.RestaurantSortOption
import com.zm.letseat.domain.restaurant.entity.RestaurantStatus
import com.zm.letseat.presentation.restaurant.model.RestaurantUi


/**
 * The restaurant item should contain :
 * the name of the restaurants,
 * the current opening state,
 * the selected sorting and the sort value for a restaurant.
 */
@Preview
@Composable
private fun RestaurantItem(
    modifier: Modifier = Modifier,
    sortingOption: RestaurantSortOption = RestaurantSortOption.RATING,
    restaurantUi: RestaurantUi = RestaurantUi(name = "Tandoori Express",
        status = RestaurantStatus.CLOSED.toString(),
        sortingValue = "22"),
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
