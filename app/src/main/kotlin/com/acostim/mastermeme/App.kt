package com.acostim.mastermeme

import android.app.Application
import com.acostim.mastermeme.di.dataModule
import com.acostim.mastermeme.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                presentationModule,
                dataModule
            )
        }
    }
}