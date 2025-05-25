package com.acostim.mastermeme.memeEditor.presentation

import androidx.compose.ui.unit.IntOffset

sealed interface MemeEditorAction {
    data class AddMemeDecor(val memeDecor: MemeDecor) : MemeEditorAction

    data class RemoveMemeDecor(val memeDecor: MemeDecor) : MemeEditorAction

    data class SelectMemeDecor(val memeDecor: MemeDecor): MemeEditorAction

    data class OpenEditDialog(val memeDecor: MemeDecor): MemeEditorAction

    data object CloseEditDialog: MemeEditorAction

    data class UpdateText(val text: String): MemeEditorAction

    data class UpdateMemeDecorOffset(val memeDecor: MemeDecor, val newOffset: IntOffset) : MemeEditorAction
}