package com.acostim.mastermeme.di

import com.acostim.mastermeme.core.data.FileManagerImpl
import com.acostim.mastermeme.core.data.MemesRepositoryImpl
import com.acostim.mastermeme.core.domain.FileManager
import com.acostim.mastermeme.core.domain.MemeRepository
import com.acostim.mastermeme.memeEditor.presentation.MemeEditorViewModel
import com.acostim.mastermeme.memeEditor.presentation.UndoRedoManager
import com.acostim.mastermeme.memeList.presentation.MemeListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::MemeListViewModel)
    viewModelOf(::MemeEditorViewModel)
    factoryOf(::UndoRedoManager)
}

val dataModule = module {
    singleOf(::MemesRepositoryImpl) bind MemeRepository::class
    factoryOf(::FileManagerImpl) bind FileManager::class
}
