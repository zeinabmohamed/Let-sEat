package com.zm.letseat.domain.restaurant.mapper

import com.zm.letseat.data.model.Restaurant
import com.zm.letseat.domain.restaurant.entity.RestaurantEntity
import com.zm.letseat.domain.restaurant.entity.RestaurantStatus

class RestaurantEntityMapper {
    private val defaultRestaurantName = ""
    private val defaultRestaurantStatus = RestaurantStatus.OPEN

    fun mapToDomain(restaurant: Restaurant) = RestaurantEntity(
        name = restaurant.name ?: defaultRestaurantName,
        status = restaurant.status?.let { status ->
            when (status) {
                "open" -> RestaurantStatus.OPEN
                "closed" -> RestaurantStatus.CLOSED
                "order ahead" -> RestaurantStatus.ORDER_AHEAD
                else -> defaultRestaurantStatus
            }
        } ?: defaultRestaurantStatus,
        sortingValues = SortingValuesEntity(
            bestMatch = restaurant.sortingValues?.bestMatch ?: 0f,
            newest = restaurant.sortingValues?.newest ?: 0f,
            ratingAverage = restaurant.sortingValues?.ratingAverage ?: 0f,
            popularity = restaurant.sortingValues?.popularity ?: 0f,
            averageProductPrice = restaurant.sortingValues?.averageProductPrice ?: 0f,
            deliveryCosts = restaurant.sortingValues?.deliveryCosts ?: 0f,
            minCost = restaurant.sortingValues?.minCost ?: 0f,
        )
    )
}

