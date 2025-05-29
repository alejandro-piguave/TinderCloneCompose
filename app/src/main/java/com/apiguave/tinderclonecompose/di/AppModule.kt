package com.apiguave.tinderclonecompose.di

import com.apiguave.feature_auth.di.authFeatureModule
import org.koin.dsl.module

val appModule = module {
    includes(dataModule)
    includes(domainModule)
    includes(presentationModule)
    includes(authFeatureModule)
}