package com.apiguave.feature_chat.di

import com.apiguave.tinderclonedomain.usecase.GetMatchesUseCase
import com.apiguave.tinderclonedomain.usecase.GetMessagesUseCase
import com.apiguave.tinderclonedomain.usecase.GetPictureUseCase
import com.apiguave.tinderclonedomain.usecase.GetProfilesUseCase
import com.apiguave.tinderclonedomain.usecase.LikeProfileUseCase
import com.apiguave.tinderclonedomain.usecase.PassProfileUseCase
import com.apiguave.tinderclonedomain.usecase.SendMessageUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetMessagesUseCase(get()) }
    factory { SendMessageUseCase(get()) }
    factory { GetPictureUseCase(get()) }
    factory { GetMatchesUseCase(get()) }
    factory { GetPictureUseCase(get()) }

}