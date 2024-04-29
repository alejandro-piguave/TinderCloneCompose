package com.apiguave.tinderclonecompose.di

import com.apiguave.tinderclonedata.repository.auth.AuthLocalDataSource
import com.apiguave.tinderclonedata.repository.auth.AuthRemoteDataSource
import com.apiguave.tinderclonedata.source.AuthRemoteDataSourceImpl
import com.apiguave.tinderclonedomain.auth.AuthRepository
import com.apiguave.tinderclonedata.source.AuthLocalDataSourceImpl
import com.apiguave.tinderclonedata.repository.auth.AuthRepositoryImpl
import com.apiguave.tinderclonedata.repository.match.MatchRemoteDataSource
import com.apiguave.tinderclonedata.repository.match.MatchRepositoryImpl
import com.apiguave.tinderclonedata.repository.message.MessageRemoteDataSource
import com.apiguave.tinderclonedata.repository.message.MessageRepositoryImpl
import com.apiguave.tinderclonedata.repository.profile.ProfileGeneratorImpl
import com.apiguave.tinderclonedata.repository.profile.ProfileRemoteDataSource
import com.apiguave.tinderclonedata.repository.profile.ProfileRepositoryImpl
import com.apiguave.tinderclonedata.source.MatchRemoteDataSourceImpl
import com.apiguave.tinderclonedomain.match.MatchRepository
import com.apiguave.tinderclonedata.source.MessageRemoteDataSourceImpl
import com.apiguave.tinderclonedomain.message.MessageRepository
import com.apiguave.tinderclonedata.source.ProfileRemoteDataSourceImpl
import com.apiguave.tinderclonedomain.profile.generator.ProfileGenerator
import com.apiguave.tinderclonedomain.profile.ProfileRepository
import org.koin.dsl.module

val dataModule = module {

    //Profile
    single<ProfileRemoteDataSource> { ProfileRemoteDataSourceImpl() }
    single<ProfileGenerator> { ProfileGeneratorImpl(get()) }
    single<ProfileRepository> { ProfileRepositoryImpl(get()) }

    //Messages
    single<MessageRemoteDataSource> { MessageRemoteDataSourceImpl() }
    single<MessageRepository> { MessageRepositoryImpl(get()) }

    //Matches
    single<MatchRemoteDataSource> { MatchRemoteDataSourceImpl() }
    single<MatchRepository> { MatchRepositoryImpl(get()) }

    //Auth
    single<AuthLocalDataSource> { AuthLocalDataSourceImpl() }
    single<AuthRemoteDataSource> { AuthRemoteDataSourceImpl() }
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
}