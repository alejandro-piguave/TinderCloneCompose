package com.apiguave.feature_home.di

import com.apiguave.tinderclonedomain.usecase.GetProfilesUseCase
import com.apiguave.tinderclonedomain.usecase.PassProfileUseCase
import com.apiguave.domain_message.usecases.SendMessageUseCase
import com.apiguave.domain_picture.usecases.GetPictureUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetProfilesUseCase(get()) }
    factory { PassProfileUseCase(get()) }
    factory { SendMessageUseCase(get()) }
    factory { GetPictureUseCase(get()) }

}