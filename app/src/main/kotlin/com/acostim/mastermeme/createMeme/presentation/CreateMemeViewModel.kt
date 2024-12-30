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
        //TODO: Not updating. To be checked
        val currentMemeDecor = memeDecorItems.find { it.id == id }
        currentMemeDecor?.offset?.copy(
            x = currentMemeDecor.offset.x + newOffset.x,
            y = currentMemeDecor.offset.y + newOffset.y
        )
    }
}