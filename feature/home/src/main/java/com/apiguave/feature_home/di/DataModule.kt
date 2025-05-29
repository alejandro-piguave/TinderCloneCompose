package com.apiguave.feature_home.di

import com.apiguave.data_picture.repository.PictureRemoteDataSource
import com.apiguave.data_picture.repository.PictureRepositoryImpl
import com.apiguave.data_picture.source.PictureRemoteDataSourceImpl
import com.apiguave.data_picture.source.PictureRemoteDataSourceMockImpl
import com.apiguave.feature_home.BuildConfig
import com.apiguave.tinderclonedata.repository.match.MatchRemoteDataSource
import com.apiguave.tinderclonedata.repository.match.MatchRepositoryImpl
import com.apiguave.tinderclonedata.repository.message.MessageRemoteDataSource
import com.apiguave.tinderclonedata.repository.message.MessageRepositoryImpl
import com.apiguave.tinderclonedata.repository.profile.ProfileRemoteDataSource
import com.apiguave.tinderclonedata.repository.profile.ProfileRepositoryImpl
import com.apiguave.tinderclonedata.source.firebase.MatchRemoteDataSourceImpl
import com.apiguave.tinderclonedata.source.firebase.MessageRemoteDataSourceImpl
import com.apiguave.tinderclonedata.source.firebase.ProfileRemoteDataSourceImpl
import com.apiguave.tinderclonedata.source.mock.MatchRemoteDataSourceMockImpl
import com.apiguave.tinderclonedata.source.mock.MessageRemoteDataSourceMockImpl
import com.apiguave.tinderclonedata.source.mock.ProfileRemoteDataSourceMockImpl
import com.apiguave.tinderclonedomain.match.MatchRepository
import com.apiguave.tinderclonedomain.message.MessageRepository
import com.apiguave.tinderclonedomain.picture.PictureRepository
import com.apiguave.tinderclonedomain.profile.ProfileRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<ProfileRepository> { ProfileRepositoryImpl(get()) }
    single<PictureRepository> { PictureRepositoryImpl(get()) }
    single<MessageRepository> { MessageRepositoryImpl(get()) }
    single<MatchRepository> { MatchRepositoryImpl(get())}
}

val sourceModule = module {
    single<PictureRemoteDataSource> { PictureRemoteDataSourceImpl() }
    single<ProfileRemoteDataSource> { ProfileRemoteDataSourceImpl() }
    single<MessageRemoteDataSource> { MessageRemoteDataSourceImpl() }
    single<MatchRemoteDataSource> { MatchRemoteDataSourceImpl() }
}

val mockSourceModule = module {
    single<PictureRemoteDataSource> { PictureRemoteDataSourceMockImpl(get()) }
    single<ProfileRemoteDataSource> { ProfileRemoteDataSourceMockImpl() }
    single<MessageRemoteDataSource> { MessageRemoteDataSourceMockImpl() }
    single<MatchRemoteDataSource> { MatchRemoteDataSourceMockImpl() }
}

val dataModule = module {
    includes(repositoryModule)
    includes(if(BuildConfig.BUILD_TYPE == "mock") mockSourceModule else sourceModule)
}