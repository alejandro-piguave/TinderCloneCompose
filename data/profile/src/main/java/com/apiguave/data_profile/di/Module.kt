package com.apiguave.data_profile.di

import com.apiguave.data_profile.repository.ProfileRemoteDataSource
import com.apiguave.data_profile.repository.ProfileRepositoryImpl
import com.apiguave.data_profile.source.ProfileRemoteDataSourceImpl
import com.apiguave.data_profile.source.ProfileRemoteDataSourceMockImpl
import com.apiguave.domain_profile.repository.ProfileRepository
import org.koin.dsl.module

val dataProfileModule = module {
    single<ProfileRepository> { ProfileRepositoryImpl(get()) }
    single<ProfileRemoteDataSource> { ProfileRemoteDataSourceImpl() }
}

val mockDataProfileModule = module {
    single<ProfileRepository> { ProfileRepositoryImpl(get()) }
    single<ProfileRemoteDataSource> { ProfileRemoteDataSourceMockImpl() }
}