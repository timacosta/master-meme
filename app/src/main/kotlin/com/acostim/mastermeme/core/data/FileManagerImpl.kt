package com.acostim.mastermeme.core.data

import android.content.Context
import android.graphics.Bitmap
import com.acostim.mastermeme.core.domain.FileManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class FileManagerImpl(
    private val context: Context
) : FileManager {

    companion object {
        private const val PNG = ".png"
    }

    override suspend fun getUris(path: String): List<String> {
        return withContext(Dispatchers.IO) {
            context.assets.list(path)?.map { fileName ->
                "$path/$fileName"
            }.orEmpty()
        }
    }

    override suspend fun saveMemeToStorage(bitmap: Bitmap, fileName: String) {
        withContext(Dispatchers.IO) {
            val file = File(context.filesDir, "$fileName$PNG")
            FileOutputStream(file).use {
                bitmap.compress(Bitmap.CompressFormat.PNG, 85, it)
            }
        }
    }
}