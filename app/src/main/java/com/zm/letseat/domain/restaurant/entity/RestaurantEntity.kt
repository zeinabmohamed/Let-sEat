package com.zm.letseat.domain.restaurant.entity

data class RestaurantEntity(
    val name: String,
    val status: RestaurantStatus,
)

enum class RestaurantStatus(val value: String) {
    OPEN("open"),
    ORDER_AHEAD("order ahead"),
    CLOSED("closed");
}