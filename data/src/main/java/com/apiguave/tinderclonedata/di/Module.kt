package com.apiguave.tinderclonedata.di

import com.apiguave.tinderclonedata.account.AccountRemoteDataSource
import com.apiguave.tinderclonedomain.account.AccountRepository
import com.apiguave.tinderclonedata.api.auth.AuthApi
import com.apiguave.tinderclonedata.api.match.MatchApi
import com.apiguave.tinderclonedata.api.message.MessageApi
import com.apiguave.tinderclonedata.api.picture.PictureApi
import com.apiguave.tinderclonedata.api.user.UserApi
import com.apiguave.tinderclonedata.account.AccountRepositoryImpl
import com.apiguave.tinderclonedata.match.MatchRepositoryImpl
import com.apiguave.tinderclonedata.message.MessageRepositoryImpl
import com.apiguave.tinderclonedata.profile.ProfileGeneratorImpl
import com.apiguave.tinderclonedata.profile.ProfileRepositoryImpl
import com.apiguave.tinderclonedata.match.MatchRemoteDataSource
import com.apiguave.tinderclonedomain.match.MatchRepository
import com.apiguave.tinderclonedata.message.MessageRemoteDataSource
import com.apiguave.tinderclonedomain.message.MessageRepository
import com.apiguave.tinderclonedata.profile.ProfileLocalDataSource
import com.apiguave.tinderclonedata.profile.ProfileRemoteDataSource
import com.apiguave.tinderclonedomain.profile.ProfileGenerator
import com.apiguave.tinderclonedomain.profile.ProfileRepository
import org.koin.dsl.module

val dataModule = module {

    //Api
    single { MatchApi() }
    single { UserApi() }
    single { PictureApi() }
    single { AuthApi() }
    single { MessageApi() }

    //Profile
    single { ProfileLocalDataSource() }
    single { ProfileRemoteDataSource(get(), get()) }
    single<ProfileGenerator> { ProfileGeneratorImpl(get()) }
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