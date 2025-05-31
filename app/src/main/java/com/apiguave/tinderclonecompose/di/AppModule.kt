package com.apiguave.tinderclonecompose.di

import com.apiguave.auth_data.di.authDataMode
import com.apiguave.auth_data.di.fakeAuthDataModule
import com.apiguave.match_data.di.dataMatchModule
import com.apiguave.match_data.di.mockDataMatchModule
import com.apiguave.message_data.di.dataMessageModule
import com.apiguave.message_data.di.mockDataMessageModule
import com.apiguave.picture_data.di.dataPictureModule
import com.apiguave.picture_data.di.mockDataPictureModule
import com.apiguave.profile_data.di.dataProfileModule
import com.apiguave.profile_data.di.mockDataProfileModule
import com.apiguave.auth_domain.di.domainAuthModule
import com.apiguave.match_domain.di.domainMatchModule
import com.apiguave.message_domain.di.domainMessageModule
import com.apiguave.picture_domain.di.domainPictureModule
import com.apiguave.profile_domain.di.domainProfileModule
import com.apiguave.onboarding_ui.BuildConfig
import com.apiguave.onboarding_ui.di.authFeatureModule
import com.apiguave.chat_ui.di.chatFeatureModule
import com.apiguave.home_ui.di.homeFeatureModule
import com.apiguave.editprofile_ui.di.profileFeatureModule
import com.apiguave.onboarding_domain.di.onboardingDomainModule
import org.koin.dsl.module

val appModule = module {
    includes(authFeatureModule)
    includes(homeFeatureModule)
    includes(chatFeatureModule)
    includes(profileFeatureModule)

    includes(domainAuthModule)
    includes(domainMatchModule)
    includes(domainMessageModule)
    includes(domainPictureModule)
    includes(domainProfileModule)

    includes(onboardingDomainModule)

    if(BuildConfig.BUILD_TYPE == "mock") {
        includes(fakeAuthDataModule)
        includes(mockDataMatchModule)
        includes(mockDataMessageModule)
        includes(mockDataPictureModule)
        includes(mockDataProfileModule)
    } else {
        includes(authDataMode)
        includes(dataMatchModule)
        includes(dataMessageModule)
        includes(dataPictureModule)
        includes(dataProfileModule)
    }

}