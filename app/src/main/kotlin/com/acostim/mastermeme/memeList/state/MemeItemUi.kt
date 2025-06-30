package com.acostim.mastermeme.memeList.state

import android.graphics.Bitmap
import com.acostim.mastermeme.core.domain.Meme
import java.time.LocalDateTime

data class MemeListUi(
    val templatesPathList: List<String> = emptyList(),
    val savedMemes: List<MemeItemUi> = emptyList(),
    val isSortOptionsVisible: Boolean = false,
    val selectedSortOption: SelectedSortOption = SelectedSortOption.FAVOURITES
)

data class MemeItemUi(
    val uid: Int,
    val bitmap: Bitmap? = null,
    val date: LocalDateTime,
    val isFavorite: Boolean
)

fun Meme.toUiModel(bitmap: Bitmap?) = MemeItemUi(
    uid = uid,
    bitmap = bitmap,
    date = date,
    isFavorite = isFavorite
)

enum class SelectedSortOption {
    FAVOURITES,
    NEWEST;
}
