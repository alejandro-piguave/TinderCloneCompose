package com.apiguave.tinderclonedata.di

import com.apiguave.tinderclonedata.account.datasource.AccountRemoteDataSource
import com.apiguave.tinderclonedata.account.repository.AccountRepository
import com.apiguave.tinderclonedata.api.auth.AuthApi
import com.apiguave.tinderclonedata.api.match.MatchApi
import com.apiguave.tinderclonedata.api.message.MessageApi
import com.apiguave.tinderclonedata.api.picture.PictureApi
import com.apiguave.tinderclonedata.api.user.UserApi
import com.apiguave.tinderclonedata.home.datasource.HomeRemoteDataSource
import com.apiguave.tinderclonedata.home.repository.HomeRepository
import com.apiguave.tinderclonedata.impl.AccountRepositoryImpl
import com.apiguave.tinderclonedata.impl.HomeRepositoryImpl
import com.apiguave.tinderclonedata.impl.MatchRepositoryImpl
import com.apiguave.tinderclonedata.impl.MessageRepositoryImpl
import com.apiguave.tinderclonedata.impl.ProfileRepositoryImpl
import com.apiguave.tinderclonedata.match.datasource.MatchRemoteDataSource
import com.apiguave.tinderclonedata.match.repository.MatchRepository
import com.apiguave.tinderclonedata.message.datasource.MessageRemoteDataSource
import com.apiguave.tinderclonedata.message.repository.MessageRepository
import com.apiguave.tinderclonedata.profile.datasource.ProfileLocalDataSource
import com.apiguave.tinderclonedata.profile.datasource.ProfileRemoteDataSource
import com.apiguave.tinderclonedata.profile.repository.ProfileRepository
import org.koin.dsl.module

val dataModule = module {

    //Api
    single { MatchApi() }
    single { UserApi() }
    single { PictureApi() }
    single { AuthApi() }
    single { MessageApi() }

    //Home
    single { HomeRemoteDataSource(get(), get()) }
    single<HomeRepository> { HomeRepositoryImpl(get(), get(), get()) }

    //Profile
    single { ProfileLocalDataSource() }
    single { ProfileRemoteDataSource(get(), get()) }
    single<ProfileRepository> { ProfileRepositoryImpl(get(), get(), get()) }

    //Messages
    single { MessageRemoteDataSource(get(), get()) }
    single<MessageRepository> { MessageRepositoryImpl(get()) }

    //Matches
    single { MatchRemoteDataSource(get(), get(), get()) }
    single<MatchRepository> { MatchRepositoryImpl(get()) }

    //Account
    single { AccountRemoteDataSource(get()) }
    single<AccountRepository> { AccountRepositoryImpl(get()) }
}