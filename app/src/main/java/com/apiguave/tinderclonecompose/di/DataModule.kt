package com.apiguave.tinderclonecompose.di

import com.apiguave.tinderclonecompose.BuildConfig
import com.apiguave.tinderclonedata.repository.auth.AuthRemoteDataSource
import com.apiguave.tinderclonedata.source.AuthRemoteDataSourceImpl
import com.apiguave.tinderclonedomain.auth.AuthRepository
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
import com.apiguave.tinderclonedata.source.mock.AuthRemoteDataSourceMockImpl
import com.apiguave.tinderclonedata.source.mock.MatchRemoteDataSourceMockImpl
import com.apiguave.tinderclonedata.source.mock.MessageRemoteDataSourceMockImpl
import com.apiguave.tinderclonedata.source.mock.ProfileRemoteDataSourceMockImpl
import com.apiguave.tinderclonedomain.profile.generator.ProfileGenerator
import com.apiguave.tinderclonedomain.profile.ProfileRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<ProfileGenerator> { ProfileGeneratorImpl(get()) }
    single<ProfileRepository> { ProfileRepositoryImpl(get()) }
    single<MessageRepository> { MessageRepositoryImpl(get()) }
    single<MatchRepository> { MatchRepositoryImpl(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
}

val sourceModule = module {
    single<ProfileRemoteDataSource> { ProfileRemoteDataSourceImpl() }
    single<MessageRemoteDataSource> { MessageRemoteDataSourceImpl() }
    single<MatchRemoteDataSource> { MatchRemoteDataSourceImpl() }
    single<AuthRemoteDataSource> { AuthRemoteDataSourceImpl() }
}

val mockSourceModule = module {
    single<ProfileRemoteDataSource> { ProfileRemoteDataSourceMockImpl(get()) }
    single<MessageRemoteDataSource> { MessageRemoteDataSourceMockImpl() }
    single<MatchRemoteDataSource> { MatchRemoteDataSourceMockImpl(get()) }
    single<AuthRemoteDataSource> { AuthRemoteDataSourceMockImpl() }
}

val dataModule = module {
    includes(repositoryModule)
    includes(if(BuildConfig.BUILD_TYPE == "mock") mockSourceModule else sourceModule)
}