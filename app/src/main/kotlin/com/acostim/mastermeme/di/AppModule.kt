package com.acostim.mastermeme.di

import androidx.room.Room
import com.acostim.mastermeme.core.data.FileManagerImpl
import com.acostim.mastermeme.core.data.MemesRepositoryImpl
import com.acostim.mastermeme.core.data.database.MasterMemeDatabase
import com.acostim.mastermeme.core.data.database.MemeDao
import com.acostim.mastermeme.core.domain.FileManager
import com.acostim.mastermeme.core.domain.MemeRepository
import com.acostim.mastermeme.memeEditor.presentation.MemeEditorViewModel
import com.acostim.mastermeme.memeEditor.presentation.UndoRedoManager
import com.acostim.mastermeme.memeList.MemeListViewModel
import org.koin.android.ext.koin.androidContext
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
    single<MasterMemeDatabase> {
        Room.databaseBuilder(
            context = androidContext(),
            klass = MasterMemeDatabase::class.java,
            name = "master_meme_db"
        ).build()
    }

    single<MemeDao> { get<MasterMemeDatabase>().memeDao() }
}
