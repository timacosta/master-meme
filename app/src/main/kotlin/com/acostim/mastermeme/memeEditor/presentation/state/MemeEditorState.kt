package com.acostim.mastermeme.memeEditor.presentation.state

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.IntOffset
import com.acostim.mastermeme.R
import com.acostim.mastermeme.core.presentation.UiText
import com.acostim.mastermeme.ui.theme.Impact
import java.util.UUID

data class MemeEditorState(
    val memeDecors: List<MemeDecor> = emptyList(),
    val selectedMemeDecor: MemeDecor? = null,
    val isStylingOptionsVisible: Boolean = false,
    val isSavingOptionsVisible: Boolean = true,
    val isInEditMode: Boolean = false
)

data class MemeDecor(
    val id: String = UUID.randomUUID().toString(),
    val text: UiText = UiText.StringResource(R.string.tap_twice_to_edit),
    val offset: IntOffset = IntOffset(0, 0),
    val fontFamily: FontFamily = Impact,
    val fontSize: Float = 40f,
    val fontColor: Color = Color.White
)