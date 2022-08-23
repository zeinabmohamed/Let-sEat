package com.zm.letseat.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zm.letseat.presentation.restaurant.RestaurantsListScreen
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // For sake of simplicity will add the main screen as [com.zm.letseat.presentation.restaurant.RestaurantsListScreen]
        // later should be replaced by NavHost to manage nav in MainActivity
        setContent {
            RestaurantsListScreen()
        }
    }
}