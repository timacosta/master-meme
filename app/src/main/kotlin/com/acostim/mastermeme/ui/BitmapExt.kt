package com.acostim.mastermeme.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.CancellationException

fun loadBitmapFromResources(context: Context, assetPath: String): Bitmap? {
    return try {
        val inputStream = context.assets.open(assetPath)
        BitmapFactory.decodeStream(inputStream)
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        e.printStackTrace()
        null
    }
}