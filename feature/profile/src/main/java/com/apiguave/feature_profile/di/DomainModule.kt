package com.apiguave.feature_profile.di

import com.apiguave.domain_auth.usecases.SignOutUseCase
import com.apiguave.domain_picture.usecases.GetPictureUseCase
import com.apiguave.domain_profile.usecases.GetProfileUseCase
import com.apiguave.domain_profile.usecases.UpdateProfileUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { SignOutUseCase(get()) }
    factory { UpdateProfileUseCase(get()) }
    factory { GetProfileUseCase(get()) }
    factory { GetPictureUseCase(get()) }

}