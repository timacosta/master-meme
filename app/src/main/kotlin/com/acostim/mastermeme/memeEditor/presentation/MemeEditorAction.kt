package com.acostim.mastermeme.memeEditor.presentation

import androidx.compose.ui.unit.IntOffset

sealed interface MemeEditorAction {
    data class AddMemeDecor(val memeDecor: MemeDecor) : MemeEditorAction

    data class RemoveMemeDecor(val id: String) : MemeEditorAction

    data class OpenEditDialog(val id: String): MemeEditorAction

    data object CloseEditDialog: MemeEditorAction

    data class UpdateText(val text: String): MemeEditorAction

    data class UpdateMemeDecorOffset(val id: String, val newOffset: IntOffset) : MemeEditorAction
}