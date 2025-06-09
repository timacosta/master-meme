package com.acostim.mastermeme.memeEditor.presentation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.ViewModel
import com.acostim.mastermeme.core.presentation.UiText
import com.acostim.mastermeme.memeEditor.presentation.state.CreateMemeState
import com.acostim.mastermeme.memeEditor.presentation.state.MemeDecor
import com.acostim.mastermeme.memeEditor.presentation.state.MemeEditorAction
import com.acostim.mastermeme.memeEditor.presentation.state.MemeEditorEvent
import com.acostim.mastermeme.memeEditor.presentation.state.MemeFont
import com.acostim.mastermeme.memeEditor.presentation.state.fonts
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

class MemeEditorViewModel : ViewModel() {

    private val _state: MutableStateFlow<CreateMemeState> = MutableStateFlow(CreateMemeState())
    val state = _state.asStateFlow()

    private val _events: Channel<MemeEditorEvent> = Channel()
    val events = _events.receiveAsFlow()

    fun onAction(action: MemeEditorAction) {
        when (action) {
            is MemeEditorAction.AddMemeDecor -> addMemeDecor(memeDecor = action.memeDecor)
            is MemeEditorAction.RemoveMemeDecor -> removeMemeDecor(memeDecor = action.memeDecor)
            is MemeEditorAction.UpdateMemeDecorOffset -> updateMemeDecorOffset(
                selectedMemeDecor = action.memeDecor,
                newOffset = action.newOffset
            )

            is MemeEditorAction.OnFocusCleared -> onMemeDecorFocusCleared()
            is MemeEditorAction.OpenStylingOptions -> onMemeDecorClick(action.memeDecor)
            is MemeEditorAction.OpenEditDialog -> openTextEditDialog(action.memeDecor)
            is MemeEditorAction.CloseEditDialog -> closeEditDialog()
            is MemeEditorAction.UpdateText -> changeTextOnConfirmation(
                id = _state.value.selectedMemeDecor?.id,
                value = action.text
            )

            is MemeEditorAction.UpdateMemeDecorFont -> onMemeFontUpdate(action.font)

            is MemeEditorAction.UpdateMemeDecorColor -> onMemeColorUpdate(action.color)

            is MemeEditorAction.UpdateMemeDecorSize -> onMemeDecorSizeUpdate(action.size)

        }
    }

    private fun addMemeDecor(memeDecor: MemeDecor) {
        _state.update {
            it.copy(memeDecors = it.memeDecors + memeDecor)
        }
    }

    private fun removeMemeDecor(memeDecor: MemeDecor) {
        _state.update { currentState ->
            currentState.copy(memeDecors = currentState.memeDecors.filter { it.id != memeDecor.id })
        }
    }

    private fun onMemeDecorClick(memeDecor: MemeDecor) {
        _state.update { currentState ->
            currentState.copy(
                selectedMemeDecor = memeDecor,
                isStylingOptionsVisible = true,
                isSavingOptionsVisible = false
            )
        }
    }

    private fun onMemeFontUpdate(font: MemeFont) {
        _state.update { currentState ->
            val decorToEdit = currentState.selectedMemeDecor
            val updatedList = currentState.memeDecors.map {
                if (decorToEdit != null && it.id == decorToEdit.id) {
                    it.copy(
                        fontFamily = font.fontFamily
                    )
                } else {
                    it
                }
            }
            currentState.copy(memeDecors = updatedList)
        }
    }

    private fun onMemeColorUpdate(color: Color) {
        _state.update { currentState ->
            val decorToEdit = currentState.selectedMemeDecor

            val updatedList = currentState.memeDecors.map {
                if (decorToEdit != null && it.id == decorToEdit.id) {
                    it.copy(
                        fontColor = color
                    )
                } else {
                    it
                }
            }

            currentState.copy(memeDecors = updatedList)
        }
    }

    private fun onMemeDecorSizeUpdate(fontSize: Float) {
        _state.update { currentState ->
            val decorToEdit = currentState.selectedMemeDecor

            val updatedList = currentState.memeDecors.map {
                if (decorToEdit != null && it.id == decorToEdit.id) {
                    it.copy(
                        fontSize = fontSize
                    )
                } else {
                    it
                }
            }

            currentState.copy(memeDecors = updatedList)
        }
    }

    private fun onMemeDecorFocusCleared() {
        _state.update { currentState ->
            currentState.copy(
                isSavingOptionsVisible = true,
                isStylingOptionsVisible = false,
                selectedMemeDecor = null
            )
        }
    }

    private fun openTextEditDialog(memeDecor: MemeDecor) {
        val decorToEdit = _state.value.memeDecors.firstOrNull { it.id == memeDecor.id }
        if (decorToEdit != null) {
            _state.update {
                it.copy(
                    selectedMemeDecor = decorToEdit,
                    showEditDialog = true
                )
            }
        }
    }

    private fun closeEditDialog() {
        _state.update {
            it.copy(
                showEditDialog = false
            )
        }
    }

    fun getCurrentMemeDecorText(memeDecor: MemeDecor?): UiText {
        return _state.value.memeDecors.first { it.id == memeDecor?.id }.text
    }

    private fun changeTextOnConfirmation(id: String?, value: String) {
        _state.update { currentState ->
            val updatedList = currentState.memeDecors.map { memeDecor ->
                if (memeDecor.id == id) {
                    memeDecor.copy(text = UiText.DynamicString(value))
                } else {
                    memeDecor
                }
            }

            currentState.copy(memeDecors = updatedList)
        }

        closeEditDialog()
    }

    private fun updateMemeDecorOffset(
        selectedMemeDecor: MemeDecor,
        newOffset: IntOffset,
    ) {
        _state.update { currentState ->
            val updatedList = currentState.memeDecors.map { memeDecor ->
                if (memeDecor.id == selectedMemeDecor.id) {
                    memeDecor.copy(offset = newOffset)
                } else {
                    memeDecor
                }
            }
            currentState.copy(memeDecors = updatedList)
        }
    }
}