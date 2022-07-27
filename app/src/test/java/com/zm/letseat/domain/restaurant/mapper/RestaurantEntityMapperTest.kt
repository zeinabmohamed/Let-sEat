package com.zm.letseat.domain.restaurant.mapper

import com.zm.letseat.data.model.Restaurant
import com.zm.letseat.data.model.SortingValues
import com.zm.letseat.domain.restaurant.entity.RestaurantEntity
import com.zm.letseat.domain.restaurant.entity.RestaurantStatus
import org.junit.Assert
import org.junit.Test

class RestaurantEntityMapperTest {

    private val sut = RestaurantEntityMapper()

    @Test
    fun `mapToDomain handle null values`() {
        // Given
        val restaurant = Restaurant(name = null, status = null)
        val expected = RestaurantEntity(name = "",
            status = RestaurantStatus.OPEN,
            sortingValues = SortingValuesEntity(
                bestMatch = 0f,
                newest = 0f,
                ratingAverage = 0f,
                popularity = 0f,
                distance = 0f,
                averageProductPrice = 0f,
                deliveryCosts = 0f,
                minCost = 0f,
            ))
        // Act
        val actual = sut.mapToDomain(restaurant)
        // Assert
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `mapToDomain return expected result`() {
        // Given
        val restaurant = Restaurant(name = "Tanoshii Sushi",
            status = "closed",
            sortingValues = SortingValues(
                bestMatch = 0f,
                newest = 96.0f,
                ratingAverage = 4.5f,
                distance = 4.5f,
                popularity = 17f,
                averageProductPrice = 1536f,
                deliveryCosts = 200f,
                minCost = 1000f,
            ))
        val expected = RestaurantEntity(name = "Tanoshii Sushi",
            status = RestaurantStatus.CLOSED,
            sortingValues = SortingValuesEntity(
                bestMatch = 0f,
                newest = 96.0f,
                ratingAverage = 4.5f,
                distance = 4.5f,
                popularity = 17f,
                averageProductPrice = 1536f,
                deliveryCosts = 200f,
                minCost = 1000f,
            ))
        // Act
        val actual = sut.mapToDomain(restaurant)
        // Assert
        Assert.assertEquals(expected, actual)
    }
}
