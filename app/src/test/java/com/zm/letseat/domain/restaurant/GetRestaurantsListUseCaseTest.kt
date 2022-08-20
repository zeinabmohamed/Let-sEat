package com.zm.letseat.domain.restaurant

import com.zm.letseat.data.model.Restaurant
import com.zm.letseat.data.model.SortingValues
import com.zm.letseat.domain.restaurant.entity.RestaurantEntity
import com.zm.letseat.domain.restaurant.entity.RestaurantSortOption.*
import com.zm.letseat.domain.restaurant.entity.RestaurantStatus
import com.zm.letseat.domain.restaurant.mapper.RestaurantEntityMapper
import com.zm.letseat.domain.restaurant.entity.SortingValuesEntity
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Test

class GetRestaurantsListUseCaseTest {
    private val repository: IRestaurantRepository = mockk()
    private val sut = GetRestaurantsListUseCase(
        restaurantRepository = repository,
        mapper = RestaurantEntityMapper()
    )

    @Test
    fun `GetRestaurantsList return default order by status`() = runBlockingTest {
        // Given
        coEvery { repository.getRestaurants() } returns listOf(
            Restaurant(name = "item1", status = "closed"),
            Restaurant(name = "item2", status = "open"),
            Restaurant(name = "item3", status = "order ahead"),
            Restaurant(name = "item4", status = "open"),
            Restaurant(name = "item5", status = "closed"),
            Restaurant(name = "item6", status = "order ahead")
        )
        val expected = listOf(
            RestaurantEntity(name = "item2", status = RestaurantStatus.OPEN),
            RestaurantEntity(name = "item4", status = RestaurantStatus.OPEN),
            RestaurantEntity(name = "item3", status = RestaurantStatus.ORDER_AHEAD),
            RestaurantEntity(name = "item6", status = RestaurantStatus.ORDER_AHEAD),
            RestaurantEntity(name = "item1", status = RestaurantStatus.CLOSED),
            RestaurantEntity(name = "item5", status = RestaurantStatus.CLOSED)
        )
        // Act
        val actual = sut.invoke()
        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `GetRestaurantsList return expected order by popularity`() = runBlockingTest {
        // Given
        coEvery { repository.getRestaurants() } returns listOf(
            Restaurant(name = "item1", sortingValues = SortingValues(popularity = 7f)),
            Restaurant(name = "item2", sortingValues = SortingValues(popularity = 2.2f)),
            Restaurant(name = "item3", sortingValues = SortingValues(popularity = 1.2f)),
            Restaurant(name = "item4", sortingValues = SortingValues(popularity = 0f)),
            Restaurant(name = "item5", sortingValues = SortingValues(popularity = 2f)),
            Restaurant(name = "item6", sortingValues = SortingValues(popularity = 4f))
        )
        val expected = listOf(
            RestaurantEntity(name = "item1", sortingValues = SortingValuesEntity(popularity = 7f)),
            RestaurantEntity(name = "item6", sortingValues = SortingValuesEntity(popularity = 4f)),
            RestaurantEntity(name = "item2",
                sortingValues = SortingValuesEntity(popularity = 2.2f)),
            RestaurantEntity(name = "item5", sortingValues = SortingValuesEntity(popularity = 2f)),
            RestaurantEntity(name = "item3",
                sortingValues = SortingValuesEntity(popularity = 1.2f)),
            RestaurantEntity(name = "item4", sortingValues = SortingValuesEntity(popularity = 0f))
        )
        // Act
        val actual = sut.invoke(sortBy = POPULARITY)
        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `GetRestaurantsList return expected order by best-match`() = runBlockingTest {
        // Given
        coEvery { repository.getRestaurants() } returns listOf(
            Restaurant(name = "item1", sortingValues = SortingValues(bestMatch = 7f)),
            Restaurant(name = "item2", sortingValues = SortingValues(bestMatch = 2.2f)),
            Restaurant(name = "item3", sortingValues = SortingValues(bestMatch = 1.2f)),
            Restaurant(name = "item4", sortingValues = SortingValues(bestMatch = 0f)),
            Restaurant(name = "item5", sortingValues = SortingValues(bestMatch = 2f)),
            Restaurant(name = "item6", sortingValues = SortingValues(bestMatch = 4f))
        )
        val expected = listOf(
            RestaurantEntity(name = "item1", sortingValues = SortingValuesEntity(bestMatch = 7f)),
            RestaurantEntity(name = "item6", sortingValues = SortingValuesEntity(bestMatch = 4f)),
            RestaurantEntity(name = "item2", sortingValues = SortingValuesEntity(bestMatch = 2.2f)),
            RestaurantEntity(name = "item5", sortingValues = SortingValuesEntity(bestMatch = 2f)),
            RestaurantEntity(name = "item3", sortingValues = SortingValuesEntity(bestMatch = 1.2f)),
            RestaurantEntity(name = "item4", sortingValues = SortingValuesEntity(bestMatch = 0f))
        )
        // Act
        val actual = sut.invoke(BEST_MATCH)
        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `GetRestaurantsList return expected order by newest`() = runBlockingTest {
        // Given
        coEvery { repository.getRestaurants() } returns listOf(
            Restaurant(name = "item1", sortingValues = SortingValues(newest = 7f)),
            Restaurant(name = "item2", sortingValues = SortingValues(newest = 2.2f)),
            Restaurant(name = "item3", sortingValues = SortingValues(newest = 1.2f)),
            Restaurant(name = "item4", sortingValues = SortingValues(newest = 0f)),
            Restaurant(name = "item5", sortingValues = SortingValues(newest = 2f)),
            Restaurant(name = "item6", sortingValues = SortingValues(newest = 4f))
        )
        val expected = listOf(
            RestaurantEntity(name = "item1", sortingValues = SortingValuesEntity(newest = 7f)),
            RestaurantEntity(name = "item6", sortingValues = SortingValuesEntity(newest = 4f)),
            RestaurantEntity(name = "item2", sortingValues = SortingValuesEntity(newest = 2.2f)),
            RestaurantEntity(name = "item5", sortingValues = SortingValuesEntity(newest = 2f)),
            RestaurantEntity(name = "item3", sortingValues = SortingValuesEntity(newest = 1.2f)),
            RestaurantEntity(name = "item4", sortingValues = SortingValuesEntity(newest = 0f))
        )
        // Act
        val actual = sut.invoke(NEWEST)
        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `GetRestaurantsList return expected order by rating average`() = runBlockingTest {
        // Given
        coEvery { repository.getRestaurants() } returns listOf(
            Restaurant(name = "item1", sortingValues = SortingValues(ratingAverage = 7f)),
            Restaurant(name = "item2", sortingValues = SortingValues(ratingAverage = 2.2f)),
            Restaurant(name = "item3", sortingValues = SortingValues(ratingAverage = 1.2f)),
            Restaurant(name = "item4", sortingValues = SortingValues(ratingAverage = 0f)),
            Restaurant(name = "item5", sortingValues = SortingValues(ratingAverage = 2f)),
            Restaurant(name = "item6", sortingValues = SortingValues(ratingAverage = 4f))
        )
        val expected = listOf(
            RestaurantEntity(name = "item1", sortingValues = SortingValuesEntity(ratingAverage = 7f)),
            RestaurantEntity(name = "item6", sortingValues = SortingValuesEntity(ratingAverage = 4f)),
            RestaurantEntity(name = "item2", sortingValues = SortingValuesEntity(ratingAverage = 2.2f)),
            RestaurantEntity(name = "item5", sortingValues = SortingValuesEntity(ratingAverage = 2f)),
            RestaurantEntity(name = "item3", sortingValues = SortingValuesEntity(ratingAverage = 1.2f)),
            RestaurantEntity(name = "item4", sortingValues = SortingValuesEntity(ratingAverage = 0f))
        )
        // Act
        val actual = sut.invoke(RATING)
        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `GetRestaurantsList return expected order by distance`() = runBlockingTest {
        // Given
        coEvery { repository.getRestaurants() } returns listOf(
            Restaurant(name = "item1", sortingValues = SortingValues(distance = 7f)),
            Restaurant(name = "item2", sortingValues = SortingValues(distance = 2.2f)),
            Restaurant(name = "item3", sortingValues = SortingValues(distance = 1.2f)),
            Restaurant(name = "item4", sortingValues = SortingValues(distance = 0f)),
            Restaurant(name = "item5", sortingValues = SortingValues(distance = 2f)),
            Restaurant(name = "item6", sortingValues = SortingValues(distance = 4f))
        )
        val expected = listOf(
            RestaurantEntity(name = "item4", sortingValues = SortingValuesEntity(distance = 0f)),
            RestaurantEntity(name = "item3", sortingValues = SortingValuesEntity(distance = 1.2f)),
            RestaurantEntity(name = "item5", sortingValues = SortingValuesEntity(distance = 2f)),
            RestaurantEntity(name = "item2", sortingValues = SortingValuesEntity(distance = 2.2f)),
            RestaurantEntity(name = "item6", sortingValues = SortingValuesEntity(distance = 4f)),
            RestaurantEntity(name = "item1", sortingValues = SortingValuesEntity(distance = 7f))
        )
        // Act
        val actual = sut.invoke(DISTANCE)
        // Assert
        assertEquals(expected, actual)
    }


    @Test
    fun `GetRestaurantsList return expected order by average product price`() = runBlockingTest {
        // Given
        coEvery { repository.getRestaurants() } returns listOf(
            Restaurant(name = "item1", sortingValues = SortingValues(averageProductPrice = 7f)),
            Restaurant(name = "item2", sortingValues = SortingValues(averageProductPrice = 2.2f)),
            Restaurant(name = "item3", sortingValues = SortingValues(averageProductPrice = 1.2f)),
            Restaurant(name = "item4", sortingValues = SortingValues(averageProductPrice = 0f)),
            Restaurant(name = "item5", sortingValues = SortingValues(averageProductPrice = 2f)),
            Restaurant(name = "item6", sortingValues = SortingValues(averageProductPrice = 4f))
        )
        val expected = listOf(
            RestaurantEntity(name = "item4",
                sortingValues = SortingValuesEntity(averageProductPrice = 0f)),
            RestaurantEntity(name = "item3",
                sortingValues = SortingValuesEntity(averageProductPrice = 1.2f)),
            RestaurantEntity(name = "item5",
                sortingValues = SortingValuesEntity(averageProductPrice = 2f)),
            RestaurantEntity(name = "item2",
                sortingValues = SortingValuesEntity(averageProductPrice = 2.2f)),
            RestaurantEntity(name = "item6",
                sortingValues = SortingValuesEntity(averageProductPrice = 4f)),
            RestaurantEntity(name = "item1",
                sortingValues = SortingValuesEntity(averageProductPrice = 7f))
        )
        // Act
        val actual = sut.invoke(PRODUCT_PRICE)
        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `GetRestaurantsList return expected order by delivery`() = runBlockingTest {
        // Given
        coEvery { repository.getRestaurants() } returns listOf(
            Restaurant(name = "item1", sortingValues = SortingValues(deliveryCosts = 7f)),
            Restaurant(name = "item2", sortingValues = SortingValues(deliveryCosts = 2.2f)),
            Restaurant(name = "item3", sortingValues = SortingValues(deliveryCosts = 1.2f)),
            Restaurant(name = "item4", sortingValues = SortingValues(deliveryCosts = 0f)),
            Restaurant(name = "item5", sortingValues = SortingValues(deliveryCosts = 2f)),
            Restaurant(name = "item6", sortingValues = SortingValues(deliveryCosts = 4f))
        )
        val expected = listOf(
            RestaurantEntity(name = "item4",
                sortingValues = SortingValuesEntity(deliveryCosts = 0f)),
            RestaurantEntity(name = "item3",
                sortingValues = SortingValuesEntity(deliveryCosts = 1.2f)),
            RestaurantEntity(name = "item5",
                sortingValues = SortingValuesEntity(deliveryCosts = 2f)),
            RestaurantEntity(name = "item2",
                sortingValues = SortingValuesEntity(deliveryCosts = 2.2f)),
            RestaurantEntity(name = "item6",
                sortingValues = SortingValuesEntity(deliveryCosts = 4f)),
            RestaurantEntity(name = "item1",
                sortingValues = SortingValuesEntity(deliveryCosts = 7f))
        )
        // Act
        val actual = sut.invoke(DELIVERY_FEES)
        // Assert
        assertEquals(expected, actual)

    }

    @Test
    fun `GetRestaurantsList return expected order by costs or the minimum cost`() =
        runBlockingTest {
            // Given
            coEvery { repository.getRestaurants() } returns listOf(
                Restaurant(name = "item1", sortingValues = SortingValues(minCost = 7f)),
                Restaurant(name = "item2", sortingValues = SortingValues(minCost = 2.2f)),
                Restaurant(name = "item3", sortingValues = SortingValues(minCost = 1.2f)),
                Restaurant(name = "item4", sortingValues = SortingValues(minCost = 0f)),
                Restaurant(name = "item5", sortingValues = SortingValues(minCost = 2f)),
                Restaurant(name = "item6", sortingValues = SortingValues(minCost = 4f))
            )
            val expected = listOf(
                RestaurantEntity(name = "item4", sortingValues = SortingValuesEntity(minCost = 0f)),
                RestaurantEntity(name = "item3", sortingValues = SortingValuesEntity(minCost = 1.2f)),
                RestaurantEntity(name = "item5", sortingValues = SortingValuesEntity(minCost = 2f)),
                RestaurantEntity(name = "item2", sortingValues = SortingValuesEntity(minCost = 2.2f)),
                RestaurantEntity(name = "item6", sortingValues = SortingValuesEntity(minCost = 4f)),
                RestaurantEntity(name = "item1", sortingValues = SortingValuesEntity(minCost = 7f))
            )
            // Act
            val actual = sut.invoke(MIN_COST)
            // Assert
            assertEquals(expected, actual)
        }
}