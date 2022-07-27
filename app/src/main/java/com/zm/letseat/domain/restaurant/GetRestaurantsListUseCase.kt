package com.zm.letseat.domain.restaurant

import com.zm.letseat.data.model.Restaurant
import com.zm.letseat.domain.restaurant.entity.RestaurantEntity
import com.zm.letseat.domain.restaurant.mapper.RestaurantEntityMapper

/**
 * Get [Restaurant] list filtered by
 */
class GetRestaurantsListUseCase constructor(
    private val restaurantRepository: IRestaurantRepository,
    private val mapper: RestaurantEntityMapper,
) {
    suspend operator fun invoke(): List<RestaurantEntity> {
        return restaurantRepository.getRestaurants().map {
            mapper.mapToDomain(it)
        }
    }
}
