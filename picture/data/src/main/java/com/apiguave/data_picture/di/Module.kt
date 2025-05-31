package com.apiguave.data_picture.di

import com.apiguave.data_picture.repository.PictureRemoteDataSource
import com.apiguave.data_picture.repository.PictureRepositoryImpl
import com.apiguave.data_picture.source.PictureRemoteDataSourceImpl
import com.apiguave.data_picture.source.PictureRemoteDataSourceMockImpl
import com.apiguave.domain_picture.repository.PictureRepository
import org.koin.dsl.module

val dataPictureModule = module {
    single<PictureRepository> { PictureRepositoryImpl(get()) }
    single<PictureRemoteDataSource> { PictureRemoteDataSourceImpl() }
}

val mockDataPictureModule = module {
    single<PictureRepository> { PictureRepositoryImpl(get()) }
    single<PictureRemoteDataSource> { PictureRemoteDataSourceMockImpl(get()) }
}