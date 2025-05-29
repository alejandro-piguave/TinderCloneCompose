package com.apiguave.feature_auth.di

import org.koin.dsl.module

val authFeatureModule = module {
    includes(dataModule)
    includes(domainModule)
    includes(presentationModule)
}