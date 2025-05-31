package com.apiguave.picture_data.di

import com.apiguave.picture_data.repository.FakePictureRepositoryImpl
import com.apiguave.picture_data.repository.PictureRepositoryImpl
import com.apiguave.picture_data.repository.PictureFirebaseDataSource
import com.apiguave.picture_domain.repository.PictureRepository
import org.koin.dsl.module

val pictureDataModule = module {
    single<PictureRepository> { PictureRepositoryImpl(get()) }
    single<PictureFirebaseDataSource> { PictureFirebaseDataSource() }
}

val fakePictureDataModule = module {
    single<PictureRepository> { FakePictureRepositoryImpl(get()) }
}