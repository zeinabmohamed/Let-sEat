package com.zm.letseat.di

import com.zm.letseat.data.restaurant.RestaurantRepository
import com.zm.letseat.domain.restaurant.IRestaurantRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RestaurantModule {
    @Binds
    abstract fun bindRestaurantRepository(
        restaurantRepository: RestaurantRepository,
    ): IRestaurantRepository
}