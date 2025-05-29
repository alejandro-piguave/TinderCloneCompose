package com.apiguave.feature_chat.di

import com.apiguave.data_picture.repository.PictureRemoteDataSource
import com.apiguave.data_picture.repository.PictureRepositoryImpl
import com.apiguave.data_picture.source.PictureRemoteDataSourceImpl
import com.apiguave.data_picture.source.PictureRemoteDataSourceMockImpl
import com.apiguave.feature_chat.BuildConfig
import com.apiguave.tinderclonedata.repository.match.MatchRemoteDataSource
import com.apiguave.tinderclonedata.repository.match.MatchRepositoryImpl
import com.apiguave.tinderclonedata.repository.message.MessageRemoteDataSource
import com.apiguave.tinderclonedata.repository.message.MessageRepositoryImpl
import com.apiguave.tinderclonedata.source.firebase.MatchRemoteDataSourceImpl
import com.apiguave.tinderclonedata.source.firebase.MessageRemoteDataSourceImpl
import com.apiguave.tinderclonedata.source.mock.MatchRemoteDataSourceMockImpl
import com.apiguave.tinderclonedata.source.mock.MessageRemoteDataSourceMockImpl
import com.apiguave.tinderclonedomain.match.MatchRepository
import com.apiguave.tinderclonedomain.message.MessageRepository
import com.apiguave.tinderclonedomain.picture.PictureRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<MessageRepository> { MessageRepositoryImpl(get()) }
    single<PictureRepository> { PictureRepositoryImpl(get()) }
    single<MatchRepository> { MatchRepositoryImpl(get())}

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