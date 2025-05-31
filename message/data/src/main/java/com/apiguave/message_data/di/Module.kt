package com.apiguave.message_data.di

import com.apiguave.message_data.repository.MessageRemoteDataSource
import com.apiguave.message_data.repository.MessageRepositoryImpl
import com.apiguave.message_data.source.MessageRemoteDataSourceImpl
import com.apiguave.message_data.source.MessageRemoteDataSourceMockImpl
import com.apiguave.message_domain.repository.MessageRepository
import org.koin.dsl.module

val dataMessageModule = module {
    single<MessageRepository> { MessageRepositoryImpl(get()) }
    single<MessageRemoteDataSource> { MessageRemoteDataSourceImpl() }
}

val mockDataMessageModule = module {
    single<MessageRepository> { MessageRepositoryImpl(get()) }
    single<MessageRemoteDataSource> { MessageRemoteDataSourceMockImpl() }
}