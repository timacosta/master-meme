package com.acostim.mastermeme.memeEditor.presentation

import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acostim.mastermeme.core.presentation.UiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MemeEditorViewModel : ViewModel() {

    private val _state: MutableStateFlow<CreateMemeState> = MutableStateFlow(CreateMemeState())
    val state = _state.asStateFlow()

    private val _events: Channel<MemeEditorEvent> = Channel()
    val events = _events.receiveAsFlow()

    fun onAction(action: MemeEditorAction) {
        when (action) {
            is MemeEditorAction.AddMemeDecor -> addMemeDecor(memeDecor = action.memeDecor)
            is MemeEditorAction.RemoveMemeDecor -> removeMemeDecor(id = action.id)
            is MemeEditorAction.UpdateMemeDecorOffset -> updateMemeDecorOffset(
                id = action.id,
                newOffset = action.newOffset
            )
            is MemeEditorAction.OpenEditDialog -> openEditDialog(action.id)
            is MemeEditorAction.CloseEditDialog -> closeEditDialog()
            is MemeEditorAction.UpdateText -> {

            }
        }
    }

    private fun addMemeDecor(memeDecor: MemeDecor) {
        _state.update {
            it.copy(memeDecors = it.memeDecors + memeDecor)
        }
    }

    private fun removeMemeDecor(id: String) {
        _state.update { currentState ->
            currentState.copy(memeDecors = currentState.memeDecors.filter { it.id != id })
        }
    }

    private fun openEditDialog(id: String) {
        val decorToEdit = _state.value.memeDecors.firstOrNull { it.id == id }
        if (decorToEdit != null) {
            _state.update {
                it.copy(
                    editingMemeDecorId = id,
                    showEditDialog = true
                )
            }
        }
    }

    private fun closeEditDialog() {
        _state.update {
            it.copy(
                editingMemeDecorId = null,
                showEditDialog = false
            )
        }
    }

    fun getCurrentMemeDecorText(id: String?): UiText {
        return _state.value.memeDecors.first { it.id == id }.text
    }

    fun onValueChange(id: String, value: String) {
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
    }

    private fun updateMemeDecorOffset(
        id: String,
        newOffset: IntOffset,
    ) {
        _state.update { currentState ->
            val updatedList = currentState.memeDecors.map { memeDecor ->
                if (memeDecor.id == id) {
                    memeDecor.copy(offset = newOffset)
                } else {
                    memeDecor
                }
            }
            currentState.copy(memeDecors = updatedList)
        }
    }
}