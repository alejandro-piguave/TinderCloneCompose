package com.apiguave.feature_home.di

import org.koin.dsl.module

val homeFeatureModule = module {
    includes(dataModule)
    includes(domainModule)
    includes(presentationModule)
}