package com.apiguave.feature_chat.di

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
import com.apiguave.domain_message.repository.MessageRepository
import com.apiguave.domain_picture.repository.PictureRepository
import com.apiguave.feature_chat.BuildConfig
import com.apiguave.tinderclonedomain.match.MatchRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<MessageRepository> { MessageRepositoryImpl(get()) }
    single<PictureRepository> { PictureRepositoryImpl(get()) }
    single<MatchRepository> { MatchRepositoryImpl(get()) }

}

val sourceModule = module {
    single<PictureRemoteDataSource> { PictureRemoteDataSourceImpl() }
    single<MessageRemoteDataSource> { MessageRemoteDataSourceImpl() }
    single<MatchRemoteDataSource> { MatchRemoteDataSourceImpl() }
}

val mockSourceModule = module {
    single<PictureRemoteDataSource> { PictureRemoteDataSourceMockImpl(get()) }
    single<MessageRemoteDataSource> { MessageRemoteDataSourceMockImpl() }
    single<MatchRemoteDataSource> { MatchRemoteDataSourceMockImpl() }
}

val dataModule = module {
    includes(repositoryModule)
    includes(if(BuildConfig.BUILD_TYPE == "mock") mockSourceModule else sourceModule)
}