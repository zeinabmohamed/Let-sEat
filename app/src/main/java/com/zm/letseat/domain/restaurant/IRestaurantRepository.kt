package com.zm.letseat.domain.restaurant

import com.zm.letseat.data.model.Restaurant

/**
 * the contract between data-domain layer for all [Restaurant] operations
 */
interface IRestaurantRepository {

    fun getRestaurants(): List<Restaurant>
}
