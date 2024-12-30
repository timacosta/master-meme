package com.acostim.mastermeme.memeList.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MemesTemplateRepository(private val context: Context) {

    companion object {
        private const val TEMPLATE_DIR = "templates"
    }

    suspend fun getMemeTemplates(): List<String> {
        return withContext(Dispatchers.IO) {
            context.assets.list("$TEMPLATE_DIR/")?.map { name ->
                "$TEMPLATE_DIR/$name"
            } ?: emptyList()
        }
    }
}