package com.apiguave.feature_home.di

import com.apiguave.data_match.repository.MatchRemoteDataSource
import com.apiguave.data_match.repository.MatchRepositoryImpl
import com.apiguave.data_match.source.MatchRemoteDataSourceImpl
import com.apiguave.data_match.source.MatchRemoteDataSourceMockImpl
import com.apiguave.data_message.repository.MessageRemoteDataSource
import com.apiguave.data_message.repository.MessageRepositoryImpl
import com.apiguave.data_message.source.MessageRemoteDataSourceImpl
import com.apiguave.data_message.source.MessageRemoteDataSourceMockImpl
import com.apiguave.data_picture.repository.PictureRemoteDataSource
import com.apiguave.data_picture.repository.PictureRepositoryImpl
import com.apiguave.data_picture.source.PictureRemoteDataSourceImpl
import com.apiguave.data_picture.source.PictureRemoteDataSourceMockImpl
import com.apiguave.data_profile.repository.ProfileRemoteDataSource
import com.apiguave.data_profile.repository.ProfileRepositoryImpl
import com.apiguave.data_profile.source.ProfileRemoteDataSourceImpl
import com.apiguave.data_profile.source.ProfileRemoteDataSourceMockImpl
import com.apiguave.domain_message.repository.MessageRepository
import com.apiguave.feature_home.BuildConfig
import com.apiguave.tinderclonedomain.match.MatchRepository
import com.apiguave.tinderclonedomain.picture.PictureRepository
import com.apiguave.tinderclonedomain.profile.ProfileRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<ProfileRepository> { ProfileRepositoryImpl(get()) }
    single<PictureRepository> { PictureRepositoryImpl(get()) }
    single<MessageRepository> { MessageRepositoryImpl(get()) }
    single<MatchRepository> { MatchRepositoryImpl(get()) }
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