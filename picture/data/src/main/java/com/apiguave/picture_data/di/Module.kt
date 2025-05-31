package com.apiguave.picture_data.di

import com.apiguave.picture_data.repository.PictureRemoteDataSource
import com.apiguave.picture_data.repository.PictureRepositoryImpl
import com.apiguave.picture_data.source.PictureRemoteDataSourceImpl
import com.apiguave.picture_data.source.PictureRemoteDataSourceMockImpl
import com.apiguave.picture_domain.repository.PictureRepository
import org.koin.dsl.module

val dataPictureModule = module {
    single<PictureRepository> { PictureRepositoryImpl(get()) }
    single<PictureRemoteDataSource> { PictureRemoteDataSourceImpl() }
}

val mockDataPictureModule = module {
    single<PictureRepository> { PictureRepositoryImpl(get()) }
    single<PictureRemoteDataSource> { PictureRemoteDataSourceMockImpl(get()) }
}