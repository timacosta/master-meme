package com.acostim.mastermeme.memeList

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acostim.mastermeme.core.domain.MemeRepository
import com.acostim.mastermeme.memeList.state.MemeListUi
import com.acostim.mastermeme.memeList.state.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream

class MemeListViewModel(
    private val repository: MemeRepository,
) : ViewModel() {
    private val _memeTemplates = MutableStateFlow(emptyList<String>())
    val memeTemplates: StateFlow<List<String>> = _memeTemplates

    private val _savedMemes = MutableStateFlow(emptyList<MemeListUi>())
    val savedMemes: StateFlow<List<MemeListUi>> = _savedMemes

    init {
        loadSavedMemes()
    }

    fun onAction(action: MemeListAction) {
        when (action) {
            is MemeListAction.LoadMemeTemplates -> loadMemeTemplates()
        }
    }

    private fun loadMemeTemplates() {
        viewModelScope.launch {
            _memeTemplates.value = repository.getMemeTemplates()
        }
    }

    private fun loadSavedMemes() {
        viewModelScope.launch {
            repository.getSavedMemes().collect { memes ->
                val uiMemes = memes.map { meme ->
                    val bitmap = uriToBitmap(meme.path)
                    meme.toUiModel(bitmap)
                }

                _savedMemes.value = uiMemes
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
}