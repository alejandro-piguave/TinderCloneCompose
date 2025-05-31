package com.apiguave.match_data.di

import com.apiguave.match_data.repository.MatchRemoteDataSource
import com.apiguave.match_data.repository.MatchRepositoryImpl
import com.apiguave.match_data.source.MatchRemoteDataSourceImpl
import com.apiguave.match_data.source.MatchRemoteDataSourceMockImpl
import com.apiguave.match_domain.repository.MatchRepository
import org.koin.dsl.module

val dataMatchModule = module {
    single<MatchRepository> { MatchRepositoryImpl(get()) }
    single<MatchRemoteDataSource> { MatchRemoteDataSourceImpl() }
}

val mockDataMatchModule = module {
    single<MatchRepository> { MatchRepositoryImpl(get()) }
    single<MatchRemoteDataSource> { MatchRemoteDataSourceMockImpl() }
}