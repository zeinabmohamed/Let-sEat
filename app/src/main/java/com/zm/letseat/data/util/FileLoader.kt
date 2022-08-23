package com.zm.letseat.data.util

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset
import javax.inject.Inject
import kotlin.jvm.Throws

class FileLoader @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    @Throws
    fun loadFileFromAsset(jsonFilePath: String): String = runCatching {
        val inputStream: InputStream = context.assets.open(jsonFilePath)
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        String(buffer, Charset.forName("UTF-8"))
    }.onFailure {
        it.printStackTrace()
    }.getOrThrow()
}