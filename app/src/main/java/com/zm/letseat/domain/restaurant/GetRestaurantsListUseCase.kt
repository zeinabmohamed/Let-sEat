package com.zm.letseat.domain.restaurant

import com.zm.letseat.data.model.Restaurant
import com.zm.letseat.domain.restaurant.entity.RestaurantEntity
import com.zm.letseat.domain.restaurant.entity.RestaurantSortOption
import com.zm.letseat.domain.restaurant.entity.RestaurantSortOption.*
import com.zm.letseat.domain.restaurant.mapper.RestaurantEntityMapper

/**
 * Get [Restaurant] list filtered by
 */
class GetRestaurantsListUseCase constructor(
    private val restaurantRepository: IRestaurantRepository,
    private val mapper: RestaurantEntityMapper,
) {
    suspend operator fun invoke(sortBy: RestaurantSortOption = STATUS): List<RestaurantEntity> {
        return restaurantRepository.getRestaurants().map {
            mapper.mapToDomain(it)
        }.sortedByOption(sortBy)
    }

    private fun List<RestaurantEntity>.sortedByOption(sortBy: RestaurantSortOption): List<RestaurantEntity> {
        return when (sortBy) {
            STATUS -> sortedBy { it.status }
            POPULARITY -> sortedByDescending { it.sortingValues.popularity }
            BEST_MATCH -> sortedByDescending { it.sortingValues.bestMatch }
            NEWEST -> sortedByDescending { it.sortingValues.newest }
        }
    }
}
