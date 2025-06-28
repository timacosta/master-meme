package com.acostim.mastermeme.memeList

sealed interface MemeListAction {
    data object LoadMemeTemplates : MemeListAction

    data class OnFavoriteClick(val isFavorite: Boolean): MemeListAction
}
