package com.zm.letseat.restaurantslist.data.util

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class FileLoaderTest {
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    val sut = FileLoader(context = appContext)

    @Test
    fun `loadFileFromAssetReturnExpectedResult`() {
        val givenJsonFilePath = "samples/restaurantslist/restaurants_list_response.json"
        val result = sut.loadFileFromAsset(givenJsonFilePath)
        assertNotNull(result)
    }

    @Test
    fun `loadNonExistFileFileFromAssetReturnNull`() {
        val givenJsonFilePath = "samples/restaurantslist/non_exist_file.json"
        val result = sut.loadFileFromAsset(givenJsonFilePath)
        assertNull(result)
    }
}