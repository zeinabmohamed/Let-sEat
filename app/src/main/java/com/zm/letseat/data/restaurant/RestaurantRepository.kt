package com.zm.letseat.data.restaurant

import com.zm.letseat.data.model.Restaurant
import com.zm.letseat.domain.restaurant.IRestaurantRepository
import javax.inject.Inject

/**
 * Responsible to load data from data source
 */
internal class RestaurantRepository @Inject constructor(
    private val dataSource: RestaurantLocalDataSource,
) : IRestaurantRepository {
    override suspend fun getRestaurants(): List<Restaurant> = runCatching {
        dataSource.getRestaurants()?.restaurants ?: emptyList()
    }.getOrDefault(emptyList())
}
