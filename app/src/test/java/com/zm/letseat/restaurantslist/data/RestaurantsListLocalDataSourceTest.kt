package com.zm.letseat.restaurantslist.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.zm.letseat.restaurantslist.data.model.Restaurant
import com.zm.letseat.restaurantslist.data.model.RestaurantsListResponse
import com.zm.letseat.restaurantslist.data.model.SortingValues
import com.zm.letseat.restaurantslist.data.util.FileLoader
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test


class RestaurantsListLocalDataSourceTest {

    private val moshi: Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
    private val fileLoaderMock = mockk<FileLoader>()
    private val sut = RestaurantsListLocalDataSource(
        fileLoader = fileLoaderMock,
        moshi = moshi
    )

    @Test
    fun `getRestaurants throw DataError when empty file`() {
        // Given
        every { fileLoaderMock.loadFileFromAsset(any()) }.returns("")
        // Act, Assert
        assertThrows(DataError::class.java) {
            sut.getRestaurants()
        }
    }

    @Test
    fun `getRestaurants throw DataError when invalid json`() {
        // Given
        every { fileLoaderMock.loadFileFromAsset(any()) }.returns("{restaurants:[}")

        // Act, Assert
        assertThrows(DataError::class.java) {
            sut.getRestaurants()
        }
    }

    @Test
    fun `getRestaurants throw DataError when empty json`() {
        // Given
        every { fileLoaderMock.loadFileFromAsset(any()) }.returns("{}")
        val expectedResult = nullResult()
        // Act
        val result = sut.getRestaurants()
        // Assert
        assertEquals(expectedResult, result)
    }


    @Test
    fun `getRestaurants throw DataError when Null restaurants`() {
        // Given
        every { fileLoaderMock.loadFileFromAsset(any()) }.returns("{restaurants:null}")

        // Act, Assert
        assertThrows(DataError::class.java) {
            sut.getRestaurants()
        }
    }

    @Test
    fun `getRestaurants return expected null values`() {
        // Given
        every { fileLoaderMock.loadFileFromAsset(any()) }.returns("{\n" +
                "    \"restaurants\": [{\n" +
                "    \"name\": null,\n" +
                "    \"status\": null,\n" +
                "    \"sortingValues\": {\n" +
                "    \"bestMatch\": null,\n" +
                "    \"newest\": null,\n" +
                "    \"ratingAverage\": null,\n" +
                "    \"distance\": null,\n" +
                "    \"popularity\": null,\n" +
                "    \"averageProductPrice\": null,\n" +
                "    \"deliveryCosts\": null,\n" +
                "    \"minCost\": null\n" +
                "}\n" +
                "}]}")
        val expectedResult = restaurantsWithNullValues()
        // Act
        val result = sut.getRestaurants()

        // Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `getRestaurants return expected result`() {
        // Given
        every { fileLoaderMock.loadFileFromAsset(any()) }.returns("{\n" +
                "    \"restaurants\": [{\n" +
                "    \"name\": \"Tanoshii Sushi\",\n" +
                "    \"status\": \"open\",\n" +
                "    \"sortingValues\": {\n" +
                "    \"bestMatch\": 0.0,\n" +
                "    \"newest\": 96.0,\n" +
                "    \"ratingAverage\": 4.5,\n" +
                "    \"distance\": 1190,\n" +
                "    \"popularity\": 17.0,\n" +
                "    \"averageProductPrice\": 1536,\n" +
                "    \"deliveryCosts\": 200,\n" +
                "    \"minCost\": 1000\n" +
                "}\n" +
                "}]}")
        val expectedResult = successRestaurantsList()
        // Act
        val result = sut.getRestaurants()

        // Assert
        assertEquals(expectedResult, result)
    }
}

//region helper functions
private fun successRestaurantsList() = RestaurantsListResponse(
    restaurants = listOf(Restaurant(
        name = "Tanoshii Sushi",
        status = "open",
        sortingValues = SortingValues(
            bestMatch = 0f,
            newest = 96.0f,
            ratingAverage = 4.5f,
            popularity = 17f,
            averageProductPrice = 1536f,
            deliveryCosts = 200f,
            minCost = 1000f,
        )
    ))
)

private fun nullResult() = RestaurantsListResponse()
private fun restaurantsWithNullValues() = RestaurantsListResponse(
    restaurants = listOf(Restaurant(sortingValues = SortingValues()))
)
//endregion