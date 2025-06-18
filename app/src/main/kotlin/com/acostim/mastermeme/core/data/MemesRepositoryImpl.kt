package com.acostim.mastermeme.core.data

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.GraphicsLayer
import com.acostim.mastermeme.core.domain.MemeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class MemesRepositoryImpl(
    private val context: Context
) : MemeRepository {

    companion object {
        private const val TEMPLATE_DIR = "templates"
        private const val PNG = ".png"
    }

    override suspend fun getMemeTemplates(): List<Meme> {
        return withContext(Dispatchers.IO) {
            context.assets.list("$TEMPLATE_DIR/")?.map { name ->
                "$TEMPLATE_DIR/$name"
            }.orEmpty()
        }
    }

    override suspend fun saveMeme(
        graphicsLayer: GraphicsLayer,
        fileName: String
    ) {
        withContext(Dispatchers.IO) {
            val imageBitmap = graphicsLayer.toImageBitmap()

            val file = File(context.filesDir, "$fileName$PNG")
            FileOutputStream(file).use {
                imageBitmap.asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 85, it)
            }
        }
    }
}