package com.apiguave.data_message.di

import com.apiguave.data_message.repository.MessageRemoteDataSource
import com.apiguave.data_message.repository.MessageRepositoryImpl
import com.apiguave.data_message.source.MessageRemoteDataSourceImpl
import com.apiguave.data_message.source.MessageRemoteDataSourceMockImpl
import com.apiguave.domain_message.repository.MessageRepository
import org.koin.dsl.module

val dataMessageModule = module {
    single<MessageRepository> { MessageRepositoryImpl(get()) }
    single<MessageRemoteDataSource> { MessageRemoteDataSourceImpl() }
}

val mockDataMessageModule = module {
    single<MessageRepository> { MessageRepositoryImpl(get()) }
    single<MessageRemoteDataSource> { MessageRemoteDataSourceMockImpl() }
}