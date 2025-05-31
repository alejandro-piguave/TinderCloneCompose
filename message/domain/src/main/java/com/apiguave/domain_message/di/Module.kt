package com.apiguave.domain_message.di

import com.apiguave.domain_message.usecases.GetMessagesUseCase
import com.apiguave.domain_message.usecases.SendMessageUseCase
import org.koin.dsl.module

val domainMessageModule = module {
    factory { GetMessagesUseCase(get()) }
    factory { SendMessageUseCase(get()) }
}