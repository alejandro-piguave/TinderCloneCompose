package com.apiguave.tinderclonecompose.di

import com.apiguave.tinderclonedata.repository.auth.AuthLocalDataSource
import com.apiguave.tinderclonedata.repository.auth.AuthRemoteDataSource
import com.apiguave.tinderclonedata.source.auth.AuthRemoteDataSourceImpl
import com.apiguave.tinderclonedomain.auth.AuthRepository
import com.apiguave.tinderclonedata.source.api.auth.AuthApi
import com.apiguave.tinderclonedata.source.local.AuthProvider
import com.apiguave.tinderclonedata.source.api.match.MatchApi
import com.apiguave.tinderclonedata.source.api.message.MessageApi
import com.apiguave.tinderclonedata.source.api.picture.PictureApi
import com.apiguave.tinderclonedata.source.api.user.UserApi
import com.apiguave.tinderclonedata.source.auth.AuthLocalDataSourceImpl
import com.apiguave.tinderclonedata.repository.auth.AuthRepositoryImpl
import com.apiguave.tinderclonedata.repository.match.MatchRemoteDataSource
import com.apiguave.tinderclonedata.repository.match.MatchRepositoryImpl
import com.apiguave.tinderclonedata.repository.message.MessageRemoteDataSource
import com.apiguave.tinderclonedata.repository.message.MessageRepositoryImpl
import com.apiguave.tinderclonedata.repository.profile.ProfileGeneratorImpl
import com.apiguave.tinderclonedata.repository.profile.ProfileRemoteDataSource
import com.apiguave.tinderclonedata.repository.profile.ProfileRepositoryImpl
import com.apiguave.tinderclonedata.source.match.MatchRemoteDataSourceImpl
import com.apiguave.tinderclonedomain.match.MatchRepository
import com.apiguave.tinderclonedata.source.message.MessageRemoteDataSourceImpl
import com.apiguave.tinderclonedomain.message.MessageRepository
import com.apiguave.tinderclonedata.source.profile.ProfileRemoteDataSourceImpl
import com.apiguave.tinderclonedomain.profile.generator.ProfileGenerator
import com.apiguave.tinderclonedomain.profile.ProfileRepository
import org.koin.dsl.module

val dataModule = module {

    //Api
    single { AuthProvider() }
    single { MatchApi(get()) }
    single { UserApi(get()) }
    single { PictureApi(get(), get()) }
    single { AuthApi() }
    single { MessageApi(get()) }

    //Profile
    single<ProfileRemoteDataSource> { ProfileRemoteDataSourceImpl(get(), get()) }
    single<ProfileGenerator> { ProfileGeneratorImpl(get()) }
    single<ProfileRepository> { ProfileRepositoryImpl(get()) }

    //Messages
    single<MessageRemoteDataSource> { MessageRemoteDataSourceImpl(get(), get()) }
    single<MessageRepository> { MessageRepositoryImpl(get()) }

    //Matches
    single<MatchRemoteDataSource> { MatchRemoteDataSourceImpl(get(), get(), get(), get()) }
    single<MatchRepository> { MatchRepositoryImpl(get()) }

    //Auth
    single<AuthLocalDataSource> { AuthLocalDataSourceImpl(get()) }
    single<AuthRemoteDataSource> { AuthRemoteDataSourceImpl(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
}