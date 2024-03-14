package com.apiguave.tinderclonecompose.data.di

import com.apiguave.tinderclonecompose.data.account.AuthRemoteDataSource
import com.apiguave.tinderclonecompose.data.datasource.FirestoreRemoteDataSource
import com.apiguave.tinderclonecompose.data.profile.ProfileLocalDataSource
import com.apiguave.tinderclonecompose.data.datasource.StorageRemoteDataSource
import com.apiguave.tinderclonecompose.data.impl.AccountRepositoryImpl
import com.apiguave.tinderclonecompose.data.impl.HomeRepositoryImpl
import com.apiguave.tinderclonecompose.data.impl.MatchRepositoryImpl
import com.apiguave.tinderclonecompose.data.impl.MessageRepositoryImpl
import com.apiguave.tinderclonecompose.data.impl.ProfileRepositoryImpl
import com.apiguave.tinderclonecompose.data.account.AccountRepository
import com.apiguave.tinderclonecompose.data.match.MatchRepository
import com.apiguave.tinderclonecompose.data.message.repository.MessageRepository
import com.apiguave.tinderclonecompose.data.profile.ProfileRepository
import com.apiguave.tinderclonecompose.data.home.HomeRepository
import com.apiguave.tinderclonecompose.data.message.datasource.MessageRemoteDataSource
import org.koin.dsl.module

val dataModule = module {

    //Data sources
    single { AuthRemoteDataSource() }
    single { FirestoreRemoteDataSource() }
    single { StorageRemoteDataSource() }
    single { ProfileLocalDataSource() }

    //Repositories
    single<AccountRepository> { AccountRepositoryImpl(get()) }
    single<MatchRepository> { MatchRepositoryImpl(get(),get(),get()) }
    single<HomeRepository> { HomeRepositoryImpl(get(), get(), get()) }
    single<ProfileRepository> { ProfileRepositoryImpl(get(),get(), get(), get()) }

    //Messages
    single { MessageRemoteDataSource() }
    single<MessageRepository> { MessageRepositoryImpl(get()) }
}