package com.acostim.mastermeme.core.domain

import androidx.compose.ui.graphics.layer.GraphicsLayer

interface MemeRepository {
    suspend fun getMemeTemplates(): List<String>

    suspend fun saveMeme(
        graphicsLayer: GraphicsLayer,
        fileName: String
    )
}