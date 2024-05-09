package com.apiguave.tinderclonecompose.di

import com.apiguave.tinderclonecompose.BuildConfig
import com.apiguave.tinderclonedata.repository.auth.AuthRemoteDataSource
import com.apiguave.tinderclonedata.source.firebase.AuthRemoteDataSourceImpl
import com.apiguave.tinderclonedomain.auth.AuthRepository
import com.apiguave.tinderclonedata.repository.auth.AuthRepositoryImpl
import com.apiguave.tinderclonedata.repository.match.MatchRemoteDataSource
import com.apiguave.tinderclonedata.repository.match.MatchRepositoryImpl
import com.apiguave.tinderclonedata.repository.message.MessageRemoteDataSource
import com.apiguave.tinderclonedata.repository.message.MessageRepositoryImpl
import com.apiguave.tinderclonedata.repository.picture.PictureRemoteDataSource
import com.apiguave.tinderclonedata.repository.picture.PictureRepositoryImpl
import com.apiguave.tinderclonedata.repository.profile.ProfileRemoteDataSource
import com.apiguave.tinderclonedata.repository.profile.ProfileRepositoryImpl
import com.apiguave.tinderclonedata.source.firebase.MatchRemoteDataSourceImpl
import com.apiguave.tinderclonedomain.match.MatchRepository
import com.apiguave.tinderclonedata.source.firebase.MessageRemoteDataSourceImpl
import com.apiguave.tinderclonedata.source.firebase.PictureRemoteDataSourceImpl
import com.apiguave.tinderclonedomain.message.MessageRepository
import com.apiguave.tinderclonedata.source.firebase.ProfileRemoteDataSourceImpl
import com.apiguave.tinderclonedata.source.mock.AuthRemoteDataSourceMockImpl
import com.apiguave.tinderclonedata.source.mock.MatchRemoteDataSourceMockImpl
import com.apiguave.tinderclonedata.source.mock.MessageRemoteDataSourceMockImpl
import com.apiguave.tinderclonedata.source.mock.PictureRemoteDataSourceMockImpl
import com.apiguave.tinderclonedata.source.mock.ProfileRemoteDataSourceMockImpl
import com.apiguave.tinderclonedomain.picture.PictureRepository
import com.apiguave.tinderclonedomain.profile.ProfileRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<ProfileRepository> { ProfileRepositoryImpl(get()) }
    single<PictureRepository> { PictureRepositoryImpl(get()) }
    single<MessageRepository> { MessageRepositoryImpl(get()) }
    single<MatchRepository> { MatchRepositoryImpl(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
}

val sourceModule = module {
    single<PictureRemoteDataSource> { PictureRemoteDataSourceImpl() }
    single<ProfileRemoteDataSource> { ProfileRemoteDataSourceImpl() }
    single<MessageRemoteDataSource> { MessageRemoteDataSourceImpl() }
    single<MatchRemoteDataSource> { MatchRemoteDataSourceImpl() }
    single<AuthRemoteDataSource> { AuthRemoteDataSourceImpl() }
}

val mockSourceModule = module {
    single<PictureRemoteDataSource> { PictureRemoteDataSourceMockImpl(get()) }
    single<ProfileRemoteDataSource> { ProfileRemoteDataSourceMockImpl() }
    single<MessageRemoteDataSource> { MessageRemoteDataSourceMockImpl() }
    single<MatchRemoteDataSource> { MatchRemoteDataSourceMockImpl() }
    single<AuthRemoteDataSource> { AuthRemoteDataSourceMockImpl() }
}

val dataModule = module {
    includes(repositoryModule)
    includes(if(BuildConfig.BUILD_TYPE == "mock") mockSourceModule else sourceModule)
}