package com.apiguave.tinderclonecompose.ui.di

import com.apiguave.tinderclonecompose.data.di.dataModule
import org.koin.dsl.module

val appModule = module {
    includes(dataModule)
    includes(presentationModule)
}