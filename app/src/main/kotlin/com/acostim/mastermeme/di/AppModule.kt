package com.acostim.mastermeme.di

import com.acostim.mastermeme.createMeme.presentation.CreateMemeViewModel
import com.acostim.mastermeme.core.data.MemesTemplateRepository
import com.acostim.mastermeme.memeList.presentation.MemeListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::MemeListViewModel)
    viewModelOf(::CreateMemeViewModel)
}

val dataModule = module {
    singleOf(::MemesTemplateRepository)
}
