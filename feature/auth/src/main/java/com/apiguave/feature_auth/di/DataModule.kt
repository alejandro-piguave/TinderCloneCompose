package com.apiguave.feature_auth.di

import com.apiguave.data_auth.repository.AuthRemoteDataSource
import com.apiguave.data_auth.repository.AuthRepositoryImpl
import com.apiguave.data_auth.source.AuthRemoteDataSourceImpl
import com.apiguave.data_auth.source.AuthRemoteDataSourceMockImpl
import com.apiguave.data_picture.repository.PictureRemoteDataSource
import com.apiguave.data_picture.repository.PictureRepositoryImpl
import com.apiguave.data_picture.source.PictureRemoteDataSourceImpl
import com.apiguave.data_picture.source.PictureRemoteDataSourceMockImpl
import com.apiguave.data_profile.repository.ProfileRemoteDataSource
import com.apiguave.data_profile.repository.ProfileRepositoryImpl
import com.apiguave.data_profile.source.ProfileRemoteDataSourceImpl
import com.apiguave.data_profile.source.ProfileRemoteDataSourceMockImpl
import com.apiguave.domain_auth.repository.AuthRepository
import com.apiguave.domain_picture.repository.PictureRepository
import com.apiguave.feature_auth.BuildConfig
import com.apiguave.tinderclonedomain.profile.ProfileRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<ProfileRepository> { ProfileRepositoryImpl(get()) }
    single<PictureRepository> { PictureRepositoryImpl(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
}

val sourceModule = module {
    single<PictureRemoteDataSource> { PictureRemoteDataSourceImpl() }
    single<ProfileRemoteDataSource> { ProfileRemoteDataSourceImpl() }
    single<AuthRemoteDataSource> { AuthRemoteDataSourceImpl() }
}

val mockSourceModule = module {
    single<PictureRemoteDataSource> { PictureRemoteDataSourceMockImpl(get()) }
    single<ProfileRemoteDataSource> { ProfileRemoteDataSourceMockImpl() }
    single<AuthRemoteDataSource> { AuthRemoteDataSourceMockImpl() }
}

val dataModule = module {
    includes(repositoryModule)
    includes(if(BuildConfig.BUILD_TYPE == "mock") mockSourceModule else sourceModule)
}