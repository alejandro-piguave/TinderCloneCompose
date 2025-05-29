package com.apiguave.feature_profile.di

import org.koin.dsl.module

val profileFeatureModule = module {
    includes(domainModule)
    includes(presentationModule)
}