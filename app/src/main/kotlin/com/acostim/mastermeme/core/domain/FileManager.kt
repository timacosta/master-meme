package com.acostim.mastermeme.core.domain

import android.graphics.Bitmap

interface FileManager {
    suspend fun getUris(path: String): List<String>

    suspend fun saveMemeToStorage(bitmap: Bitmap, fileName: String)
}