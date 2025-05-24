package com.acostim.mastermeme.memeEditor.presentation

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.IntOffset
import com.acostim.mastermeme.R
import com.acostim.mastermeme.core.presentation.UiText
import com.acostim.mastermeme.ui.theme.Impact
import java.util.UUID

data class CreateMemeState(
    val memeDecors: List<MemeDecor> = emptyList(),
    val editingMemeDecorId: String? = null,
    val showEditDialog: Boolean = false
)

data class MemeDecor(
    val id: String = UUID.randomUUID().toString(),
    val text: UiText = UiText.StringResource(R.string.tap_twice_to_edit),
    val offset: IntOffset = IntOffset(0, 0),
    val fontFamily: FontFamily = Impact
)