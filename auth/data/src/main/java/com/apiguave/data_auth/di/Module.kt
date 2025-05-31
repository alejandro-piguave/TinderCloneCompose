package com.apiguave.data_auth.di

import com.apiguave.data_auth.repository.AuthRemoteDataSource
import com.apiguave.data_auth.repository.AuthRepositoryImpl
import com.apiguave.data_auth.source.AuthRemoteDataSourceImpl
import com.apiguave.domain_auth.repository.AuthRepository
import org.koin.dsl.module

val dataAuthModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<AuthRemoteDataSource> { AuthRemoteDataSourceImpl() }
}

val mockDataAuthModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<AuthRemoteDataSource> { AuthRemoteDataSourceImpl() }
}