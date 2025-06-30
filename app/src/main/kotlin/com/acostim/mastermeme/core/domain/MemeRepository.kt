package com.acostim.mastermeme.core.domain

import android.graphics.Bitmap
import android.net.Uri
import kotlinx.coroutines.flow.Flow

interface MemeRepository {

    fun getSavedMemes(): Flow<List<Meme>>

    suspend fun getMemeTemplates(): List<String>

    suspend fun saveMemeToStorage(
        bitmap: Bitmap,
        fileName: String
    )

    suspend fun saveMemeToCache(
        bitmap: Bitmap,
        fileName: String
    ): Uri?

    suspend fun isFavorite(id: Int, isFavorite: Boolean)
}