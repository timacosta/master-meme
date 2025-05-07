package com.acostim.mastermeme.memeEditor.presentation

import androidx.compose.ui.unit.IntOffset

sealed interface MemeEditorAction {
    data class AddMemeDecor(val memeDecor: MemeDecor): MemeEditorAction

    data class UpdateMemeDecor(val memeDecorId: String, val newOffset: IntOffset): MemeEditorAction
}