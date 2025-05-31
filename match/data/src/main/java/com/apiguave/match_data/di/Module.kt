package com.apiguave.match_data.di

import com.apiguave.match_data.repository.FakeMatchRepositoryImpl
import com.apiguave.match_data.repository.MatchRemoteDataSource
import com.apiguave.match_data.repository.MatchRepositoryImpl
import com.apiguave.match_data.source.MatchRemoteDataSourceImpl
import com.apiguave.match_domain.repository.MatchRepository
import org.koin.dsl.module

val matchDataModule = module {
    single<MatchRepository> { MatchRepositoryImpl(get()) }
    single<MatchRemoteDataSource> { MatchRemoteDataSourceImpl() }
}

val fakeMatchDataModule = module {
    single<MatchRepository> { FakeMatchRepositoryImpl() }
}