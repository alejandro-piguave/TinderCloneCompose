package com.apiguave.data_match.di

import com.apiguave.data_match.repository.MatchRemoteDataSource
import com.apiguave.data_match.repository.MatchRepositoryImpl
import com.apiguave.data_match.source.MatchRemoteDataSourceImpl
import com.apiguave.data_match.source.MatchRemoteDataSourceMockImpl
import com.apiguave.domain_match.repository.MatchRepository
import org.koin.dsl.module

val dataMatchModule = module {
    single<MatchRepository> { MatchRepositoryImpl(get()) }
    single<MatchRemoteDataSource> { MatchRemoteDataSourceImpl() }
}

val mockDataMatchModule = module {
    single<MatchRepository> { MatchRepositoryImpl(get()) }
    single<MatchRemoteDataSource> { MatchRemoteDataSourceMockImpl() }
}