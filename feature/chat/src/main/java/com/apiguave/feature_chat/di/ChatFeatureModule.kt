package com.apiguave.feature_chat.di

import org.koin.dsl.module

val chatFeatureModule = module {
    includes(domainModule)
    includes(presentationModule)
}