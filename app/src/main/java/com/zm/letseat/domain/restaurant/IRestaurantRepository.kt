package com.zm.letseat.domain.restaurant

import com.zm.letseat.data.model.Restaurant
import com.zm.letseat.domain.restaurant.entity.RestaurantSortOption

/**
 * the contract between data-domain layer for all [Restaurant] operations
 */
interface IRestaurantRepository {

    suspend fun getRestaurants(): List<Restaurant>
    fun saveSortOption(sortBy: RestaurantSortOption)
    fun getLastSortOption(): RestaurantSortOption?
}
