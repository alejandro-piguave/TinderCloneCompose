package com.apiguave.tinderclonecompose.di

import com.apiguave.data_auth.di.dataAuthModule
import com.apiguave.data_auth.di.mockDataAuthModule
import com.apiguave.data_match.di.dataMatchModule
import com.apiguave.data_match.di.mockDataMatchModule
import com.apiguave.data_message.di.dataMessageModule
import com.apiguave.data_message.di.mockDataMessageModule
import com.apiguave.data_picture.di.dataPictureModule
import com.apiguave.data_picture.di.mockDataPictureModule
import com.apiguave.data_profile.di.dataProfileModule
import com.apiguave.data_profile.di.mockDataProfileModule
import com.apiguave.feature_auth.BuildConfig
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

    if(BuildConfig.BUILD_TYPE == "mock") {
        includes(mockDataAuthModule)
        includes(mockDataMatchModule)
        includes(mockDataMessageModule)
        includes(mockDataPictureModule)
        includes(mockDataProfileModule)
    } else {
        includes(dataAuthModule)
        includes(dataMatchModule)
        includes(dataMessageModule)
        includes(dataPictureModule)
        includes(dataProfileModule)
    }

}