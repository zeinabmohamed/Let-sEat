package com.zm.letseat.data.util

import android.content.SharedPreferences
import javax.inject.Inject

class LocalCacheManager @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) {
    fun putString(key: String, value: String) {
        with(sharedPreferences.edit()) {
            putString(key, value)
            apply()
        }
    }

    fun getString(key: String, defaultValue: String): String? {
        return sharedPreferences.getString(key, defaultValue)
    }
}