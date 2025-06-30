package com.acostim.mastermeme.memeList

sealed interface MemeListAction {
    data object LoadMemeTemplates : MemeListAction

    data class OnFavoriteClick(val id: Int, val isFavorite: Boolean): MemeListAction
}
