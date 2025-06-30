package com.acostim.mastermeme.memeList

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
}