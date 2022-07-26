package com.zm.letseat.data.restaurant

import com.zm.letseat.data.model.Restaurant
import com.zm.letseat.domain.restaurant.IRestaurantRepository

/**
 * Responsible to load data from data source
 */
internal class RestaurantRepository constructor(
    private val dataSource: RestaurantLocalDataSource,
) : IRestaurantRepository {
    override fun getRestaurants(): List<Restaurant> = runCatching {
        dataSource.getRestaurants()?.restaurants ?: emptyList()
    }.getOrDefault(emptyList())
}
