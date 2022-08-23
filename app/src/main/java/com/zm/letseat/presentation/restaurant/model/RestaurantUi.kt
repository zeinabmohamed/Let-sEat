package com.zm.letseat.presentation.restaurant.model

import com.zm.letseat.domain.restaurant.entity.RestaurantEntity
import com.zm.letseat.domain.restaurant.entity.RestaurantSortOption
import com.zm.letseat.domain.restaurant.entity.RestaurantStatus

class RestaurantUi(val name: String, val status: RestaurantStatus, val sortingValue: String)

infix fun RestaurantEntity.sortedBy(sortingOption: RestaurantSortOption): String {
    return when (sortingOption) {
        RestaurantSortOption.STATUS -> ""
        RestaurantSortOption.POPULARITY -> this.sortingValues.popularity
        RestaurantSortOption.BEST_MATCH -> this.sortingValues.bestMatch
        RestaurantSortOption.NEWEST -> this.sortingValues.newest
        RestaurantSortOption.RATING -> this.sortingValues.ratingAverage
        RestaurantSortOption.DISTANCE -> this.sortingValues.distance
        RestaurantSortOption.PRODUCT_PRICE -> this.sortingValues.averageProductPrice
        RestaurantSortOption.DELIVERY_FEES -> this.sortingValues.deliveryCosts
        RestaurantSortOption.MIN_COST -> this.sortingValues.minCost
    }.toString()
}