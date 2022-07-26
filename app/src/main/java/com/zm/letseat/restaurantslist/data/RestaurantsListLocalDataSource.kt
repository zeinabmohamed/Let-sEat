package com.zm.letseat.restaurantslist.data

import com.squareup.moshi.Moshi
import com.zm.letseat.restaurantslist.data.model.Restaurant
import com.zm.letseat.restaurantslist.data.model.RestaurantsListResponse
import com.zm.letseat.restaurantslist.data.util.FileLoader
import kotlin.jvm.Throws

/**
 * Fetch the restaurants list from the local data [restaurants_list_response.json]
 */
class RestaurantsListLocalDataSource constructor(
    private val fileLoader: FileLoader,
    private val moshi: Moshi,
) {

    @Throws(DataError::class)
    fun getRestaurants(): RestaurantsListResponse? = runCatching {
        moshi.adapter(RestaurantsListResponse::class.java)
            .fromJson(fileLoader.loadFileFromAsset(
                RESTAURANT_LIST_JSON_FILE_PATH
            ))
    }.onFailure {
        it.printStackTrace()
    }.getOrElse { throw DataError() }

    companion object {
        private const val RESTAURANT_LIST_JSON_FILE_PATH: String =
            "samples/restaurantslist/restaurants_list_response.json"
    }
}
