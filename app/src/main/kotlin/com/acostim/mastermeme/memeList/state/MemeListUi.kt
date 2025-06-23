package com.acostim.mastermeme.memeList.state

import android.graphics.Bitmap
import com.acostim.mastermeme.core.domain.Meme
import java.time.LocalDateTime

data class MemeListUi(
    val uid: Int,
    val bitmap: Bitmap? = null,
    val date: LocalDateTime,
    val isFavorite: Boolean
)

fun Meme.toUiModel(bitmap: Bitmap?) = MemeListUi(
    uid = uid,
    bitmap = bitmap,
    date = date,
    isFavorite = isFavorite
)
