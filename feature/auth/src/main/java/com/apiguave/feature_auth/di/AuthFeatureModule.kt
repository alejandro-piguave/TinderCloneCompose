package com.apiguave.feature_auth.di

import org.koin.dsl.module

val authFeatureModule = module {
    includes(domainModule)
    includes(presentationModule)
}