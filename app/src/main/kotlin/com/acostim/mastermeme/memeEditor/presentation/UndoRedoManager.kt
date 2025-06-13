package com.acostim.mastermeme.memeEditor.presentation

import com.acostim.mastermeme.memeEditor.presentation.state.MemeEditorState

class UndoRedoManager {
    private val undoStack = ArrayDeque<MemeEditorState>()
    private val redoStack = ArrayDeque<MemeEditorState>()

    fun addAction(action: MemeEditorState) {
        undoStack.addLast(action)
        redoStack.clear()
    }

    fun undo(currentState: MemeEditorState): MemeEditorState? {
        return if (undoStack.isNotEmpty()) {
            val previousState = undoStack.removeLast()
            redoStack.addLast(currentState)

            previousState.copy(
                selectedMemeDecor = currentState.selectedMemeDecor,
                isStylingOptionsVisible = currentState.isStylingOptionsVisible,
                isSavingOptionsVisible = currentState.isSavingOptionsVisible,
                isInEditMode = currentState.isInEditMode
            )
        } else null
    }

    fun redo(currentState: MemeEditorState): MemeEditorState? {
        return if (redoStack.isNotEmpty()) {
            val nextState = redoStack.removeLast()
            undoStack.addLast(currentState)

            nextState.copy(
                selectedMemeDecor = currentState.selectedMemeDecor,
                isStylingOptionsVisible = currentState.isStylingOptionsVisible,
                isSavingOptionsVisible = currentState.isSavingOptionsVisible,
                isInEditMode = currentState.isInEditMode
            )
        } else null
    }
}