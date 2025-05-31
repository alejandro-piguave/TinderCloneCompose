package com.apiguave.message_domain.di

import com.apiguave.message_domain.usecases.GetMessagesUseCase
import com.apiguave.message_domain.usecases.SendMessageUseCase
import org.koin.dsl.module

val domainMessageModule = module {
    factory { GetMessagesUseCase(get()) }
    factory { SendMessageUseCase(get()) }
}