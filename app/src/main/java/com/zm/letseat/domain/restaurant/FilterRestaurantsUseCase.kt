package com.zm.letseat.domain.restaurant

import com.zm.letseat.data.model.Restaurant
import com.zm.letseat.domain.restaurant.entity.RestaurantEntity
import com.zm.letseat.domain.restaurant.entity.RestaurantSortOption
import javax.inject.Inject

/**
 * Get [Restaurant] list filtered by
 */
class FilterRestaurantsUseCase @Inject constructor(
    private val getRestaurantsListUseCase: GetRestaurantsListUseCase,
) {
    suspend operator fun invoke(
        searchText: String,
    ): List<RestaurantEntity> {
        val result = getRestaurantsListUseCase()
        return result.second.takeIf { it.isNotEmpty() }?.filter {
            it.name.startsWith(searchText, ignoreCase = true)
        }?.run {
            this
        } ?: run {
            result.second
        }
    }
}
