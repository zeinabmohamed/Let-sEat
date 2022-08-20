package com.zm.letseat.domain.restaurant.entity

data class RestaurantEntity(
    val name: String,
    val status: RestaurantStatus = RestaurantStatus.CLOSED,
    val sortingValues: SortingValuesEntity = SortingValuesEntity(),
)

