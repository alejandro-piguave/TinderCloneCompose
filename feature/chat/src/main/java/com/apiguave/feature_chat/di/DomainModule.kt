package com.apiguave.feature_chat.di

import com.apiguave.tinderclonedomain.usecase.GetMatchesUseCase
import com.apiguave.domain_message.usecases.GetMessagesUseCase
import com.apiguave.domain_message.usecases.SendMessageUseCase
import com.apiguave.domain_picture.usecases.GetPictureUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetMessagesUseCase(get()) }
    factory { SendMessageUseCase(get()) }
    factory { GetPictureUseCase(get()) }
    factory { GetMatchesUseCase(get()) }

}