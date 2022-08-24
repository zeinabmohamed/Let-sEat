package com.zm.letseat.data.restaurant

import com.squareup.moshi.Moshi
import com.zm.letseat.data.DataError
import com.zm.letseat.data.model.RestaurantsListResponse
import com.zm.letseat.data.util.FileLoader
import com.zm.letseat.data.util.LocalCacheManager
import javax.inject.Inject
import kotlin.jvm.Throws

/**
 * Fetch the restaurants list from the local data [restaurants_list_response.json]
 */
class RestaurantLocalDataSource @Inject constructor(
    private val fileLoader: FileLoader,
    private val moshi: Moshi,
    private val localCacheManager: LocalCacheManager,
) {
    @Throws(DataError::class)
    suspend fun getRestaurants(): RestaurantsListResponse? = runCatching {
        moshi.adapter(RestaurantsListResponse::class.java)
            .fromJson(fileLoader.loadFileFromAsset(
                RESTAURANT_LIST_JSON_FILE_PATH
            ))
    }.onFailure {
        it.printStackTrace()
    }.getOrElse { throw DataError() }

    fun saveSortOption(sortOption: String) {
        localCacheManager.putString(LAST_SORT_OPTION_KEY, sortOption)
    }

    fun getSortOption() = localCacheManager.getString(LAST_SORT_OPTION_KEY, "")

    companion object {
        private const val RESTAURANT_LIST_JSON_FILE_PATH: String =
            "samples/restaurantslist/restaurants_list_response.json"
        private const val LAST_SORT_OPTION_KEY = "LastSortOption"

    }
}
