package com.apiguave.feature_chat.di

import com.apiguave.tinderclonedomain.usecase.GetMatchesUseCase
import com.apiguave.domain_message.usecases.GetMessagesUseCase
import com.apiguave.tinderclonedomain.usecase.GetPictureUseCase
import com.apiguave.domain_message.usecases.SendMessageUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { com.apiguave.domain_message.usecases.GetMessagesUseCase(get()) }
    factory { com.apiguave.domain_message.usecases.SendMessageUseCase(get()) }
    factory { GetPictureUseCase(get()) }
    factory { GetMatchesUseCase(get()) }
    factory { GetPictureUseCase(get()) }

}