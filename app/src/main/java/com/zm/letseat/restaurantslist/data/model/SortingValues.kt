package com.zm.letseat.restaurantslist.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SortingValues(
    @Json(name = "bestMatch") val bestMatch: Float? = null,
    @Json(name = "newest") val newest: Float? = null,
    @Json(name = "ratingAverage") val ratingAverage: Float? = null,
    @Json(name = "popularity") val popularity: Float? = null,
    @Json(name = "averageProductPrice") val averageProductPrice: Float? = null,
    @Json(name = "deliveryCosts") val deliveryCosts: Float? = null,
    @Json(name = "minCost") val minCost: Float? = null,
)