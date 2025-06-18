package com.acostim.mastermeme.memeList.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acostim.mastermeme.core.data.MemesRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MemeListViewModel(
    private val repository: MemesRepositoryImpl,
) : ViewModel() {
    private val _memeTemplates = MutableStateFlow(emptyList<String>())
    val memeTemplates: StateFlow<List<String>> = _memeTemplates

    fun loadMemeTemplates() {
        viewModelScope.launch {
            _memeTemplates.value = repository.getMemeTemplates()
        }
    }
}