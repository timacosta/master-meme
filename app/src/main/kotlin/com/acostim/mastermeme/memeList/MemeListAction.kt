package com.acostim.mastermeme.memeList

import com.acostim.mastermeme.memeList.state.MemeItemUi
import com.acostim.mastermeme.memeList.state.SelectedSortOption

sealed interface MemeListAction {
    data object LoadMemeTemplates : MemeListAction

    data class OnFavoriteClick(val id: Int, val isFavorite: Boolean) : MemeListAction

    data object OpenSortOptions : MemeListAction

    data class ToggleSortOptionsVisibility(val expanded: Boolean) : MemeListAction

    data class ToggleSortOption(val selectedSortOption: SelectedSortOption) : MemeListAction

    data class OnLongPress(val meme: MemeItemUi) : MemeListAction

    data class OnSelectedMeme(val meme: MemeItemUi) : MemeListAction

    data object CancelSelection : MemeListAction
}
