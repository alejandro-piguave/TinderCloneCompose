package com.apiguave.match_data.di

import com.apiguave.match_data.repository.FakeMatchRepositoryImpl
import com.apiguave.match_data.repository.MatchRepositoryImpl
import com.apiguave.match_data.repository.MatchFirebaseDataSource
import com.apiguave.match_domain.repository.MatchRepository
import org.koin.dsl.module

val matchDataModule = module {
    single<MatchRepository> { MatchRepositoryImpl(get()) }
    single<MatchFirebaseDataSource> { MatchFirebaseDataSource() }
}

val fakeMatchDataModule = module {
    single<MatchRepository> { FakeMatchRepositoryImpl() }
}