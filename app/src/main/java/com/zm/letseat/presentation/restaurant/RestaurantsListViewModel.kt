package com.zm.letseat.presentation.restaurant

import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zm.letseat.domain.restaurant.GetRestaurantsListUseCase
import com.zm.letseat.domain.restaurant.entity.RestaurantSortOption
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.zm.letseat.presentation.restaurant.model.RestaurantUi
import com.zm.letseat.presentation.restaurant.model.sortedBy
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RestaurantsListViewModel @Inject constructor(
    private val getRestaurantsListUseCase: GetRestaurantsListUseCase,
) : ViewModel() {
    private val defaultSortByOption = RestaurantSortOption.STATUS
    var uiState by mutableStateOf(RestaurantsListUiState(
        sortByOption = defaultSortByOption,
    ))
        private set

    init {
        // initial state load restaurants sorted by [RestaurantSortOption.STATUS] or last stored sort option
        loadRestaurantsList()
    }

    fun loadRestaurantsList(
        sortByOption: RestaurantSortOption? = null,
    ) {
        viewModelScope.launch {
            uiState = RestaurantsListUiState(loading = true)
            val result = getRestaurantsListUseCase.invoke(sortByOption)
            val restaurantsList = result.second.map {
                RestaurantUi(
                    name = it.name,
                    status = it.status.toString(),
                    sortingValue = (it sortedBy result.first))
            }
            uiState = RestaurantsListUiState(
                loading = false,
                sortByOption = result.first,
                restaurants = restaurantsList
            )
        }
    }
}

data class RestaurantsListUiState(
    val loading: Boolean = false,
    val sortByOption: RestaurantSortOption = RestaurantSortOption.STATUS,
    val restaurants: List<RestaurantUi> = emptyList(),
)