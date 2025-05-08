package com.acostim.mastermeme.memeEditor.presentation

import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.ViewModel
import com.acostim.mastermeme.core.presentation.UiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MemeEditorViewModel : ViewModel() {

    private val _state: MutableStateFlow<CreateMemeState> = MutableStateFlow(CreateMemeState())
    val state = _state.asStateFlow()

    fun onAction(action: MemeEditorAction) {
        when (action) {
            is MemeEditorAction.AddMemeDecor -> addMemeDecor(memeDecor = action.memeDecor)
            is MemeEditorAction.RemoveMemeDecor -> removeMemeDecor(id = action.id)
            is MemeEditorAction.UpdateMemeDecor -> updateMemeDecorOffset(
                id = action.memeDecorId,
                newOffset = action.newOffset
            )
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