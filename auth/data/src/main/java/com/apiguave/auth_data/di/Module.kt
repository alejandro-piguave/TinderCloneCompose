package com.apiguave.auth_data.di

import com.apiguave.auth_data.repository.AuthRemoteDataSource
import com.apiguave.auth_data.repository.AuthRepositoryImpl
import com.apiguave.auth_data.repository.FakeAuthRepositoryImpl
import com.apiguave.auth_data.source.AuthRemoteDataSourceImpl
import com.apiguave.auth_domain.repository.AuthRepository
import org.koin.dsl.module

val authDataMode = module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<AuthRemoteDataSource> { AuthRemoteDataSourceImpl() }
}

val fakeAuthDataModule = module {
    single<AuthRepository> { FakeAuthRepositoryImpl() }
}