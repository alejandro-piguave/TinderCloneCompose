package com.apiguave.tinderclonecompose.data.di

import com.apiguave.tinderclonecompose.data.datasource.AuthRemoteDataSource
import com.apiguave.tinderclonecompose.data.datasource.FirestoreRemoteDataSource
import com.apiguave.tinderclonecompose.data.datasource.StorageRemoteDataSource
import com.apiguave.tinderclonecompose.data.repository.*
import com.apiguave.tinderclonecompose.domain.account.AccountRepository
import com.apiguave.tinderclonecompose.domain.match.MatchRepository
import com.apiguave.tinderclonecompose.domain.message.MessageRepository
import com.apiguave.tinderclonecompose.domain.profile.ProfileRepository
import com.apiguave.tinderclonecompose.domain.profilecard.ProfileCardRepository
import org.koin.dsl.module

val dataModule = module {

    //Data sources
    single { AuthRemoteDataSource() }
    single { FirestoreRemoteDataSource() }
    single { StorageRemoteDataSource() }

    //Repositories
    single<AccountRepository> { AccountRepositoryImpl(get()) }
    single<MatchRepository> { MatchRepositoryImpl(get(),get(),get()) }
    single<MessageRepository> { MessageRepositoryImpl(get()) }
    single<ProfileCardRepository> { ProfileCardRepositoryImpl(get(), get()) }
    single<ProfileRepository> { ProfileRepositoryImpl(get(),get(),get()) }
}