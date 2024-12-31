package com.acostim.mastermeme.createMeme.presentation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.ViewModel

class CreateMemeViewModel : ViewModel() {

    val memeDecorItems = mutableStateListOf<MemeDecor>()

    fun addMemeDecor(memeDecor: MemeDecor) {
        memeDecorItems.add(memeDecor)
    }

    fun updateMemeDecorOffset(
        id: String,
        newOffset: IntOffset,
    ) {
        val index = memeDecorItems.indexOfFirst { it.id == id }

        if (index != -1) {
            val currentMemeDecor = memeDecorItems[index]

            val updatedOffset = currentMemeDecor.offset.copy(
                x = currentMemeDecor.offset.x + newOffset.x,
                y = currentMemeDecor.offset.y + newOffset.y
            )

            val updatedMemeDecor = currentMemeDecor.copy(offset = updatedOffset)

            memeDecorItems[index] = updatedMemeDecor
        }
    }
}