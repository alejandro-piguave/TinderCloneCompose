package com.apiguave.tinderclonecompose.di

import com.apiguave.feature_auth.di.authFeatureModule
import com.apiguave.feature_chat.di.chatFeatureModule
import com.apiguave.feature_home.di.homeFeatureModule
import com.apiguave.feature_profile.di.profileFeatureModule
import org.koin.dsl.module

val appModule = module {
    includes(authFeatureModule)
    includes(homeFeatureModule)
    includes(chatFeatureModule)
    includes(profileFeatureModule)
}