package com.zm.letseat.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RestaurantsListResponse(
    @Json(name = "restaurants") val restaurants: List<Restaurant>? = null,
)

