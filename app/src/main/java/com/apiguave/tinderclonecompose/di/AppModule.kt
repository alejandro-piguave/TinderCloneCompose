package com.apiguave.tinderclonecompose.di

import com.apiguave.tinderclonedata.di.dataModule
import org.koin.dsl.module

val appModule = module {
    includes(dataModule)
    includes(domainModule)
    includes(presentationModule)
}