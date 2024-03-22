package com.apiguave.tinderclonecompose.main

import android.app.Application
import com.apiguave.tinderclonecompose.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@MainApp)
            modules(appModule)
        }
    }
}