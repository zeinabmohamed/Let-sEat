package com.zm.letseat.restaurantslist.data.util

import android.content.Context
import java.io.InputStream
import java.nio.charset.Charset

class FileLoader constructor(private val context: Context) {

    fun loadFileFromAsset(jsonFilePath: String): String? = runCatching {
        val inputStream: InputStream = context.assets.open(jsonFilePath)
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        String(buffer, Charset.forName("UTF-8"))
    }.getOrNull()
}