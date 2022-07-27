package com.zm.letseat.domain.restaurant.entity

import com.zm.letseat.domain.restaurant.mapper.SortingValuesEntity

data class RestaurantEntity(
    val name: String,
    val status: RestaurantStatus,
    val sortingValues: SortingValuesEntity,
)

enum class RestaurantStatus(val value: String) {
    OPEN("open"),
    ORDER_AHEAD("order ahead"),
    CLOSED("closed");
}