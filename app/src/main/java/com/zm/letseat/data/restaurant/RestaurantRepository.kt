package com.zm.letseat.data.restaurant

import com.zm.letseat.data.model.Restaurant
import com.zm.letseat.domain.restaurant.IRestaurantRepository
import com.zm.letseat.domain.restaurant.entity.RestaurantSortOption
import javax.inject.Inject

/**
 * Responsible to load data from data source
 */
class RestaurantRepository @Inject constructor(
    private val dataSource: RestaurantLocalDataSource,
) : IRestaurantRepository {
    override suspend fun getRestaurants(): List<Restaurant> = runCatching {
        dataSource.getRestaurants()?.restaurants ?: emptyList()
    }.getOrDefault(emptyList())

    override fun saveSortOption(sortBy: RestaurantSortOption) {
        dataSource.saveSortOption(sortBy.toString())
    }

    override fun getLastSortOption(): RestaurantSortOption? {
        return dataSource.getSortOption()?.takeIf { it.isNotBlank() }?.run {
            RestaurantSortOption.valueOf(this)
        }
    }
}
