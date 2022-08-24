package com.zm.letseat.data.util

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.zm.letseat.data.util.FileLoader
import java.io.FileNotFoundException
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class FileLoaderTest {
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    val sut = FileLoader(context = appContext)

    @Test
    fun `loadFileFromAssetReturnExpectedResult`() {
        // Given
        val givenJsonFilePath = "samples/restaurantslist/restaurants_list_response.json"
        // Act
        val result = sut.loadFileFromAsset(givenJsonFilePath)
        // Assert
        assertNotNull(result)
    }

    @Test
    fun `loadNonExistFileFileFromAssetThrowException`() {
        // Given
        val givenJsonFilePath = "samples/restaurantslist/non_exist_file.json"
        // Act, Assert
        assertThrows(
            FileNotFoundException::class.java
        ) { sut.loadFileFromAsset(givenJsonFilePath) }
    }
}