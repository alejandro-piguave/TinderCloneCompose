package com.apiguave.message_data.di

import com.apiguave.message_data.repository.FakeMessageRepositoryImpl
import com.apiguave.message_data.repository.MessageRemoteDataSource
import com.apiguave.message_data.repository.MessageRepositoryImpl
import com.apiguave.message_data.source.MessageRemoteDataSourceImpl
import com.apiguave.message_domain.repository.MessageRepository
import org.koin.dsl.module

val messageDataModule = module {
    single<MessageRepository> { MessageRepositoryImpl(get()) }
    single<MessageRemoteDataSource> { MessageRemoteDataSourceImpl() }
}

val fakeMessageDataModule = module {
    single<MessageRepository> { FakeMessageRepositoryImpl() }
}