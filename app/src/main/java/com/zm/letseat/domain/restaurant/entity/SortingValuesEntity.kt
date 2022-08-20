package com.zm.letseat.domain.restaurant.entity

data class SortingValuesEntity(
    val bestMatch: Float = 0f,
    val newest: Float = 0f,
    val ratingAverage: Float = 0f,
    val distance: Float = 0f,
    val popularity: Float = 0f,
    val averageProductPrice: Float = 0f,
    val deliveryCosts: Float = 0f,
    val minCost: Float = 0f,
)