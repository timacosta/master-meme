package com.acostim.mastermeme.di

import com.acostim.mastermeme.memeEditor.presentation.MemeEditorViewModel
import com.acostim.mastermeme.core.data.MemesTemplateRepository
import com.acostim.mastermeme.memeEditor.presentation.UndoRedoManager
import com.acostim.mastermeme.memeList.presentation.MemeListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::MemeListViewModel)
    viewModelOf(::MemeEditorViewModel)
    factoryOf(::UndoRedoManager)
}

val dataModule = module {
    singleOf(::MemesTemplateRepository)
}
