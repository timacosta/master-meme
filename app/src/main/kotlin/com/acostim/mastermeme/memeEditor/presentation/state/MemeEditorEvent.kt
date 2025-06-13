package com.acostim.mastermeme.memeEditor.presentation.state

sealed interface MemeEditorEvent {
    data object NavigateBack: MemeEditorEvent
}