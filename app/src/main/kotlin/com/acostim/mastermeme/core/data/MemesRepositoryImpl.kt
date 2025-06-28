package com.acostim.mastermeme.core.data

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
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

    override suspend fun saveMemeToStorage(
        bitmap: Bitmap,
        fileName: String
    ) {
        withContext(Dispatchers.IO) {
            val file = File(context.filesDir, "$fileName$PNG").apply {
                outputStream().use { out ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 85, out)
                }
            }

            val memeEntity = MemeEntity(
                path = file.absolutePath,
                name = fileName
            )

            dao.insert(memeEntity)
        }
    }

    override suspend fun saveMemeToCache(bitmap: Bitmap, fileName: String): Uri? {
        return withContext(Dispatchers.IO) {
            try {
                val file = File(context.cacheDir, "$fileName$PNG").apply {
                    outputStream().use { out ->
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                    }
                }

                val uri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.provider",
                    file
                )

                uri
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}