package com.acostim.mastermeme.memeList

sealed interface MemeListAction {
    data object LoadMemeTemplates : MemeListAction
}
