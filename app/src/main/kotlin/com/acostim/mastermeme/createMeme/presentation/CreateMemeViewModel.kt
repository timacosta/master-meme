package com.acostim.mastermeme.createMeme.presentation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.ViewModel

class CreateMemeViewModel : ViewModel() {

    val memeDecorItems = mutableStateListOf<MemeDecor>()

    fun addMemeDecor(memeDecor: MemeDecor) {
        memeDecorItems.add(memeDecor)
    }

    fun onValueChange(id: String, value: String) {
        val index = memeDecorItems.indexOfFirst { it.id == id }

        if (index != -1) {
            val currentMemeDecor = memeDecorItems[index]

            val updatedMemeDecor = currentMemeDecor.copy(text = value)

            memeDecorItems[index] = updatedMemeDecor
        }
    }

    fun updateMemeDecorOffset(
        id: String,
        newOffset: IntOffset,
    ) {
        val index = memeDecorItems.indexOfFirst { it.id == id }

        if (index != -1) {
            val currentMemeDecor = memeDecorItems[index]

            val updatedMemeDecor = currentMemeDecor.copy(offset = newOffset)

            memeDecorItems[index] = updatedMemeDecor
        }
    }
}