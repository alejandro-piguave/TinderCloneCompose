package com.apiguave.tinderclonedata.di

import com.apiguave.tinderclonedata.auth.AuthRemoteDataSource
import com.apiguave.tinderclonedomain.auth.AuthRepository
import com.apiguave.tinderclonedata.api.auth.AuthApi
import com.apiguave.tinderclonedata.api.auth.AuthProvider
import com.apiguave.tinderclonedata.api.match.MatchApi
import com.apiguave.tinderclonedata.api.message.MessageApi
import com.apiguave.tinderclonedata.api.picture.PictureApi
import com.apiguave.tinderclonedata.api.user.UserApi
import com.apiguave.tinderclonedata.auth.AuthLocalDataSource
import com.apiguave.tinderclonedata.auth.AuthRepositoryImpl
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
    single { AuthProvider() }
    single { MatchApi(get()) }
    single { UserApi(get()) }
    single { PictureApi() }
    single { AuthApi() }
    single { MessageApi(get()) }

    //Profile
    single { ProfileLocalDataSource() }
    single { ProfileRemoteDataSource(get(), get()) }
    single<ProfileGenerator> { ProfileGeneratorImpl(get()) }
    single<ProfileRepository> { ProfileRepositoryImpl(get(), get()) }

    //Messages
    single { MessageRemoteDataSource(get(), get()) }
    single<MessageRepository> { MessageRepositoryImpl(get()) }

    //Matches
    single { MatchRemoteDataSource(get(), get(), get(), get()) }
    single<MatchRepository> { MatchRepositoryImpl(get()) }

    //Auth
    single { AuthLocalDataSource(get()) }
    single { AuthRemoteDataSource(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
}