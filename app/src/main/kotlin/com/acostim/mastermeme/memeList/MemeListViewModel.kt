package com.acostim.mastermeme.memeList

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acostim.mastermeme.core.domain.Meme
import com.acostim.mastermeme.core.domain.MemeRepository
import com.acostim.mastermeme.memeList.state.MemeItemUi
import com.acostim.mastermeme.memeList.state.MemeListUi
import com.acostim.mastermeme.memeList.state.SelectedSortOption
import com.acostim.mastermeme.memeList.state.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream

class MemeListViewModel(
    private val repository: MemeRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(MemeListUi())
    val state = _state.asStateFlow()

    private var currentSortOption = SelectedSortOption.FAVOURITES

    init {
        loadSavedMemes()
    }

    fun onAction(action: MemeListAction) {
        when (action) {
            is MemeListAction.LoadMemeTemplates -> loadMemeTemplates()
            is MemeListAction.OnFavoriteClick -> markAsFavorite(
                id = action.id,
                isFavorite = action.isFavorite
            )

            is MemeListAction.OpenSortOptions -> openSortOptions()

            is MemeListAction.ToggleSortOptionsVisibility -> {
                _state.update {
                    it.copy(
                        isSortOptionsVisible = !_state.value.isSortOptionsVisible
                    )
                }
            }

            is MemeListAction.ToggleSortOption -> {
                _state.update {
                    it.copy(selectedSortOption = action.selectedSortOption)
                }
                sortSavedMemes(action.selectedSortOption)
            }

            is MemeListAction.OnLongPress -> {
                _state.update {
                    it.copy(isSelectedMode = true)
                }
                toggleMemeSelection(action.meme.uid)
            }

            is MemeListAction.OnSelectedMeme -> toggleMemeSelection(action.meme.uid)

            is MemeListAction.CancelSelection -> cancelSelection()

            is MemeListAction.ShareSelectedMemes -> shareSelectedMemes(action.context)

            is MemeListAction.OnDelete -> delete(action.meme)
        }
    }

    private fun loadMemeTemplates() {
        viewModelScope.launch {
            _state.update {
                it.copy(templatesPathList = repository.getMemeTemplates())
            }
        }
    }

    private fun loadSavedMemes() {
        viewModelScope.launch {
            repository.getSavedMemes().collect { memes ->
                val uiMemes = memes.map { meme ->
                    val bitmap = uriToBitmap(meme.path)
                    meme.toUiModel(bitmap)
                }

                val sortedMemes = sortMemes(uiMemes, currentSortOption)

                _state.update {
                    it.copy(savedMemes = sortedMemes)
                }
            }
        }
    }

    private fun uriToBitmap(path: String): Bitmap? {
        return try {
            FileInputStream(File(path)).use { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun markAsFavorite(
        id: Int,
        isFavorite: Boolean,
    ) {
        viewModelScope.launch {
            repository.isFavorite(
                id = id,
                isFavorite = isFavorite
            )
        }
    }

    private fun openSortOptions() {
        _state.update {
            it.copy(isSortOptionsVisible = true)
        }
    }

    private fun sortSavedMemes(selectedSortOption: SelectedSortOption) {
        currentSortOption = selectedSortOption
        _state.update { currentState ->
            val sortedList = sortMemes(currentState.savedMemes, selectedSortOption)
            currentState.copy(savedMemes = sortedList)
        }
    }

    private fun sortMemes(
        list: List<MemeItemUi>,
        sort: SelectedSortOption
    ): List<MemeItemUi> {
        return when (sort) {
            SelectedSortOption.NEWEST -> list.sortedByDescending { it.date }
            SelectedSortOption.FAVOURITES -> list.sortedWith(
                compareByDescending<MemeItemUi> { it.isFavorite }
                    .thenByDescending { it.date }
            )
        }
    }

    private fun toggleMemeSelection(uid: Int) {
        _state.update { memesList ->
            val updatedList = memesList.savedMemes.map { meme ->
                if (meme.uid == uid) {
                    meme.copy(isSelected = !meme.isSelected)
                } else {
                    meme
                }
            }
            memesList.copy(savedMemes = updatedList)
        }
    }

    private fun cancelSelection() {
        _state.update {
            it.copy(
                isSelectedMode = false,
                savedMemes = it.savedMemes.map {
                    it.copy(isSelected = false)
                }
            )
        }
    }

    private fun shareSelectedMemes(context: Context) {
        viewModelScope.launch {
            val selectedMemes = _state.value.savedMemes.filter { it.isSelected }

            if (selectedMemes.isEmpty()) return@launch

            val uris = mutableListOf<Uri>()

            selectedMemes.forEach { meme ->
                meme.bitmap?.let { bitmap ->
                    val uri = repository.saveMemeToCache(
                        bitmap = bitmap,
                        fileName = "shared_meme_${meme.uid}"
                    )
                    uri?.let { uris.add(it) }
                }
            }

            if (uris.isNotEmpty()) {
                val intent = Intent().apply {
                    if (uris.size == 1) {
                        action = Intent.ACTION_SEND
                        type = "image/png"
                        putExtra(Intent.EXTRA_STREAM, uris.first())
                    } else {
                        action = Intent.ACTION_SEND_MULTIPLE
                        type = "image/png"
                        putParcelableArrayListExtra(Intent.EXTRA_STREAM, ArrayList(uris))
                    }
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }

                context.startActivity(Intent.createChooser(intent, "Share Memes"))
            }
        }
    }

    private fun delete(memes: List<MemeItemUi>) {
        viewModelScope.launch {
            repository.delete(
                memes.map { it.uid }
            )
        }
    }
}