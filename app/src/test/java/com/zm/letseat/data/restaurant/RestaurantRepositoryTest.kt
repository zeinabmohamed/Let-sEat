package com.zm.letseat.data.restaurant

import com.zm.letseat.data.DataError
import com.zm.letseat.data.model.Restaurant
import com.zm.letseat.data.model.RestaurantsListResponse
import com.zm.letseat.data.model.SortingValues
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class RestaurantRepositoryTest {
    private var dataSource: RestaurantLocalDataSource = mockk()

    private val sut = RestaurantRepository(
        dataSource
    )

    @Test
    fun `getRestaurants return empty list for Null result`() {
        // Given
        every { dataSource.getRestaurants() } returns RestaurantsListResponse(restaurants = null)
        val expectedResult = emptyList<Restaurant>()
        // Act
        val result = sut.getRestaurants()
        // Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `getRestaurants return empty list for DataError result`() {
        // Given
        every { dataSource.getRestaurants() } throws DataError("Fail to load data")
        val expectedResult = emptyList<Restaurant>()
        // Act
        val result = sut.getRestaurants()
        // Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `getRestaurants return expected result`() {
        // Given
        every { dataSource.getRestaurants() } returns successRestaurantsResponse
        val expectedResult = successRestaurantsResponse.restaurants
        // Act
        val result = sut.getRestaurants()
        // Assert
        assertEquals(expectedResult, result)
    }
}

private val successRestaurantsResponse = RestaurantsListResponse(
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