package com.zm.letseat.domain.restaurant.mapper

data class SortingValuesEntity(
    val bestMatch: Float,
    val newest: Float,
    val ratingAverage: Float,
    val popularity: Float,
    val averageProductPrice: Float,
    val deliveryCosts: Float,
    val minCost: Float,
)