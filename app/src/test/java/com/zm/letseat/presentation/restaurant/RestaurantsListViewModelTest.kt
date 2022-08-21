package com.zm.letseat.presentation.restaurant

import com.zm.letseat.domain.restaurant.GetRestaurantsListUseCase
import com.zm.letseat.domain.restaurant.entity.RestaurantEntity
import com.zm.letseat.domain.restaurant.entity.RestaurantSortOption
import com.zm.letseat.domain.restaurant.entity.RestaurantStatus
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RestaurantsListViewModelTest {
    private val givenSortedRestaurantsListByStatus: List<RestaurantEntity> =
        listOf(RestaurantEntity(name = "item1",
            status = RestaurantStatus.ORDER_AHEAD))

    private val givenSortedRestaurantsListByRating: List<RestaurantEntity> =
        listOf(RestaurantEntity(name = "item2",
            status = RestaurantStatus.ORDER_AHEAD))

    private val getRestaurantsListUseCaseMocked: GetRestaurantsListUseCase = mockk()
    private lateinit var sut: RestaurantsListViewModel

    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        coEvery { getRestaurantsListUseCaseMocked(sortBy = RestaurantSortOption.STATUS) } returns givenSortedRestaurantsListByStatus
        coEvery { getRestaurantsListUseCaseMocked(sortBy = RestaurantSortOption.RATING) } returns givenSortedRestaurantsListByRating
        sut = RestaurantsListViewModel(
            getRestaurantsListUseCaseMocked
        )
    }

    @Test
    fun `initial state load restaurants sorted by Status`() {
        // Given
        val expectDefaultUiState = RestaurantsListUiState(
            sortByOption = RestaurantSortOption.STATUS,
            restaurants = givenSortedRestaurantsListByStatus
        )
        // Assert
        assertEquals(expectDefaultUiState, sut.uiState)
    }

    @Test
    fun `load restaurants sorted by Rating`() {
        // Given
        val expectDefaultUiState = RestaurantsListUiState(
            sortByOption = RestaurantSortOption.RATING,
            restaurants = givenSortedRestaurantsListByRating
        )
        // Act
        sut.loadRestaurantsList(RestaurantSortOption.RATING)
        // Assert
        assertEquals(expectDefaultUiState, sut.uiState)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}