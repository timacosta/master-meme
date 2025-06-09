package com.acostim.mastermeme.memeEditor.presentation

import com.acostim.mastermeme.memeEditor.presentation.state.CreateMemeState

class MemeEditorStateManagerImpl(
    private val maxHistorySize: Int = 5
) {
    private val undoStack = ArrayDeque<CreateMemeState>()
    private val redoStack = ArrayDeque<CreateMemeState>()

    var currentState: CreateMemeState = CreateMemeState()
        private set

    val canUndo: Boolean = undoStack.isNotEmpty()
    val canRedo: Boolean = redoStack.isNotEmpty()

    fun applyChange(newState: CreateMemeState) {
        if (newState != currentState) {
            if (undoStack.size == maxHistorySize) undoStack.removeFirst()
            undoStack.addLast(currentState)
            currentState = newState
            redoStack.clear()
        }
    }

    fun undo() {
        if (canUndo) {
            redoStack.addLast(currentState)
            currentState = undoStack.removeLast()
        }
    }

    fun redo() {
        if (canRedo) {
            undoStack.addLast(currentState)
            currentState = redoStack.removeLast()
        }
    }

    fun clearHistory() {
        undoStack.clear()
        redoStack.clear()
    }
}