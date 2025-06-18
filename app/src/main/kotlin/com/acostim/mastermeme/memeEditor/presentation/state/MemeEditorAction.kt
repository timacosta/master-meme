package com.acostim.mastermeme.memeEditor.presentation.state

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.unit.IntOffset

sealed interface MemeEditorAction {
    data class StoreBackgroundImage(val bitmap: Bitmap) : MemeEditorAction

    data class AddMemeDecor(val memeDecor: MemeDecor) : MemeEditorAction

    data class RemoveMemeDecor(val memeDecor: MemeDecor) : MemeEditorAction

    data object OnFocusCleared : MemeEditorAction

    data class OpenStylingOptions(val memeDecor: MemeDecor) : MemeEditorAction

    data class OpenEditDialog(val memeDecor: MemeDecor) : MemeEditorAction

    data object CloseEditDialog : MemeEditorAction

    data class UpdateText(val text: String) : MemeEditorAction

    data class UpdateMemeDecorOffset(val memeDecor: MemeDecor, val newOffset: IntOffset) :
        MemeEditorAction

    data class UpdateMemeDecorFont(val font: MemeFont) : MemeEditorAction

    data class UpdateMemeDecorColor(val color: Color) : MemeEditorAction

    data class UpdateMemeDecorSize(val size: Float) : MemeEditorAction

    data object Undo : MemeEditorAction

    data object Redo : MemeEditorAction

    data object DiscardLatestChange : MemeEditorAction

    data object OnExitDialog : MemeEditorAction

    data object OnDismissExitDialog : MemeEditorAction

    data object OnConfirmExitDialog : MemeEditorAction

    data class SaveMeme(val graphicsLayer: GraphicsLayer) : MemeEditorAction
}