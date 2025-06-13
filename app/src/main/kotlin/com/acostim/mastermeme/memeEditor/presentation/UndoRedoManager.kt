package com.acostim.mastermeme.memeEditor.presentation

import com.acostim.mastermeme.memeEditor.presentation.state.MemeDecor

class UndoRedoManager {
    private val undoStack = ArrayDeque<List<MemeDecor>>()
    private val redoStack = ArrayDeque<List<MemeDecor>>()

    fun addAction(memeDecors: List<MemeDecor>) {
        undoStack.addLast(memeDecors.map { it.copy() })
        redoStack.clear()
    }

    fun addToRedoStack(memeDecors: List<MemeDecor>) {
        redoStack.addLast(memeDecors.map { it.copy() })
    }

    fun undo(): List<MemeDecor>? {
        return if (undoStack.isNotEmpty()) {
            val previousDecors = undoStack.removeLast()
            previousDecors
        } else null
    }

    fun redo(): List<MemeDecor>? {
        return if (redoStack.isNotEmpty()) {
            val nextDecors = redoStack.removeLast()
            nextDecors
        } else null
    }

    fun canUndo(): Boolean = undoStack.isNotEmpty()
    fun canRedo(): Boolean = redoStack.isNotEmpty()
}