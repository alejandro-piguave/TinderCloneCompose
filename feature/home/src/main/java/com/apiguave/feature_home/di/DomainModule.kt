package com.apiguave.feature_home.di

import com.apiguave.tinderclonedomain.usecase.GetPictureUseCase
import com.apiguave.tinderclonedomain.usecase.GetProfilesUseCase
import com.apiguave.tinderclonedomain.usecase.LikeProfileUseCase
import com.apiguave.tinderclonedomain.usecase.PassProfileUseCase
import com.apiguave.domain_message.usecases.SendMessageUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetProfilesUseCase(get()) }
    factory { LikeProfileUseCase(get(), get()) }
    factory { PassProfileUseCase(get()) }
    factory { com.apiguave.domain_message.usecases.SendMessageUseCase(get()) }
    factory { GetPictureUseCase(get()) }

}