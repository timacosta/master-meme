package com.acostim.mastermeme.createMeme.presentation

import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.ViewModel
import com.acostim.mastermeme.core.presentation.UiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CreateMemeViewModel : ViewModel() {

    private val _state: MutableStateFlow<CreateMemeState> = MutableStateFlow(CreateMemeState())
    val state = _state.asStateFlow()

    fun addMemeDecor(memeDecor: MemeDecor) {
        _state.update {
            it.copy(memeDecors = it.memeDecors + memeDecor)
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

    fun updateMemeDecorOffset(
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