package com.zm.letseat.presentation.restaurant

import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zm.letseat.domain.restaurant.GetRestaurantsListUseCase
import com.zm.letseat.domain.restaurant.entity.RestaurantSortOption
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.zm.letseat.domain.restaurant.entity.RestaurantEntity

class RestaurantsListViewModel constructor(
    private val getRestaurantsListUseCase: GetRestaurantsListUseCase,
) : ViewModel() {
    private val defaultSortByOption = RestaurantSortOption.STATUS
    var uiState by mutableStateOf(RestaurantsListUiState(
        sortByOption = defaultSortByOption,
    ))
        private set

    init {
        // initial state load restaurants sorted by [RestaurantSortOption.STATUS]
        loadRestaurantsList(defaultSortByOption)
    }

    fun loadRestaurantsList(
        sortByOption: RestaurantSortOption = defaultSortByOption,
    ) {
        viewModelScope.launch {
            uiState = RestaurantsListUiState(loading = true)
            val restaurantsList = getRestaurantsListUseCase.invoke(sortByOption)
            uiState = RestaurantsListUiState(
                loading = false,
                sortByOption = sortByOption,
                restaurants = restaurantsList
            )
        }
    }
}

data class RestaurantsListUiState(
    val loading: Boolean = false,
    val sortByOption: RestaurantSortOption = RestaurantSortOption.STATUS,
    val restaurants: List<RestaurantEntity> = emptyList(),
)