package com.apiguave.tinderclonecompose.di

import org.koin.dsl.module

val appModule = module {
    includes(dataModule)
    includes(domainModule)
    includes(presentationModule)
}