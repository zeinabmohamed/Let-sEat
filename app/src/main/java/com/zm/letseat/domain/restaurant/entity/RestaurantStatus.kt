package com.zm.letseat.domain.restaurant.entity

/**
 * Restaurant Status ordered should be respected
 */
enum class RestaurantStatus(val value: String) {
    OPEN("open"),
    ORDER_AHEAD("order ahead"),
    CLOSED("closed");
}