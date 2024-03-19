package com.apiguave.tinderclonecompose.presentation.di

import com.apiguave.tinderclonecompose.data.di.dataModule
import org.koin.dsl.module

val appModule = module {
    includes(dataModule)
    includes(presentationModule)
}