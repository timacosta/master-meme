package com.acostim.mastermeme.core.data

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.GraphicsLayer
import com.acostim.mastermeme.core.data.database.MemeDao
import com.acostim.mastermeme.core.data.database.MemeEntity
import com.acostim.mastermeme.core.data.database.toDomain
import com.acostim.mastermeme.core.domain.Meme
import com.acostim.mastermeme.core.domain.MemeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class MemesRepositoryImpl(
    private val context: Context,
    private val dao: MemeDao
) : MemeRepository {

    companion object {
        private const val TEMPLATE_DIR = "templates"
        private const val PNG = ".png"
    }

    override fun getSavedMemes(): Flow<List<Meme>> {
        return dao.getSavedMemes().map { entities ->
            entities.map { entity ->
                entity.toDomain()
            }
        }
    }

    override suspend fun getMemeTemplates(): List<String> {
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

            val memeEntity = MemeEntity(
                path = file.absolutePath,
                name = fileName
            )

            dao.insert(memeEntity)
        }
    }
}