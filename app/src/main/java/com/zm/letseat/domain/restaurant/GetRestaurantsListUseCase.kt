package com.zm.letseat.domain.restaurant

import androidx.lifecycle.DEFAULT_ARGS_KEY
import com.zm.letseat.data.model.Restaurant
import com.zm.letseat.domain.restaurant.entity.RestaurantEntity
import com.zm.letseat.domain.restaurant.entity.RestaurantSortOption
import com.zm.letseat.domain.restaurant.entity.RestaurantSortOption.*
import com.zm.letseat.domain.restaurant.mapper.RestaurantEntityMapper
import javax.inject.Inject

/**
 * Get [Restaurant] list filtered by
 */
class GetRestaurantsListUseCase @Inject constructor(
    private val restaurantRepository: IRestaurantRepository,
    private val mapper: RestaurantEntityMapper,
) {

    companion object {
        private val DEFAULT_SORT_OPTION = STATUS
    }

    suspend operator fun invoke(selectedSelectedOption: RestaurantSortOption? = null): Pair<RestaurantSortOption, List<RestaurantEntity>> {
        (selectedSelectedOption
            ?: restaurantRepository.getLastSortOption()
            ?: DEFAULT_SORT_OPTION).let { sortBy ->
            return restaurantRepository.getRestaurants().map {
                mapper.mapToDomain(it)
            }.sortedByOption(sortBy).let {
                // business update : retain sort last sort option
                // will save it in case of success result
                restaurantRepository.saveSortOption(sortBy)
                Pair(sortBy, it)
            }
        }
    }

    private fun List<RestaurantEntity>.sortedByOption(sortBy: RestaurantSortOption): List<RestaurantEntity> {
        return when (sortBy) {
            STATUS -> sortedBy { it.status }
            POPULARITY -> sortedByDescending { it.sortingValues.popularity }
            BEST_MATCH -> sortedByDescending { it.sortingValues.bestMatch }
            NEWEST -> sortedByDescending { it.sortingValues.newest }
            RATING -> sortedByDescending { it.sortingValues.ratingAverage }
            DISTANCE -> sortedBy { it.sortingValues.distance }
            PRODUCT_PRICE -> sortedBy { it.sortingValues.averageProductPrice }
            DELIVERY_FEES -> sortedBy { it.sortingValues.deliveryCosts }
            MIN_COST -> sortedBy { it.sortingValues.minCost }
        }
    }
}
