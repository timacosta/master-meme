package com.acostim.mastermeme.core.domain

import androidx.compose.ui.graphics.layer.GraphicsLayer
import kotlinx.coroutines.flow.Flow

interface MemeRepository {

    fun getSavedMemes(): Flow<List<Meme>>

    suspend fun getMemeTemplates(): List<String>

    suspend fun saveMeme(
        graphicsLayer: GraphicsLayer,
        fileName: String
    )
}