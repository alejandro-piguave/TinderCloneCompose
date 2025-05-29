package com.apiguave.feature_profile.di

import com.apiguave.data_auth.repository.AuthRemoteDataSource
import com.apiguave.data_auth.repository.AuthRepositoryImpl
import com.apiguave.data_auth.source.AuthRemoteDataSourceImpl
import com.apiguave.data_auth.source.AuthRemoteDataSourceMockImpl
import com.apiguave.domain_auth.repository.AuthRepository
import com.apiguave.feature_profile.BuildConfig
import com.apiguave.tinderclonedata.repository.picture.PictureRemoteDataSource
import com.apiguave.tinderclonedata.repository.picture.PictureRepositoryImpl
import com.apiguave.tinderclonedata.repository.profile.ProfileRemoteDataSource
import com.apiguave.tinderclonedata.repository.profile.ProfileRepositoryImpl
import com.apiguave.tinderclonedata.source.firebase.PictureRemoteDataSourceImpl
import com.apiguave.tinderclonedata.source.firebase.ProfileRemoteDataSourceImpl
import com.apiguave.tinderclonedata.source.mock.PictureRemoteDataSourceMockImpl
import com.apiguave.tinderclonedata.source.mock.ProfileRemoteDataSourceMockImpl
import com.apiguave.tinderclonedomain.picture.PictureRepository
import com.apiguave.tinderclonedomain.profile.ProfileRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<ProfileRepository> { ProfileRepositoryImpl(get()) }
    single<PictureRepository> { PictureRepositoryImpl(get()) }

}

val sourceModule = module {
    single<AuthRemoteDataSource> { AuthRemoteDataSourceImpl() }
    single<PictureRemoteDataSource> { PictureRemoteDataSourceImpl() }
    single<ProfileRemoteDataSource> { ProfileRemoteDataSourceImpl() }
}

val mockSourceModule = module {
    single<AuthRemoteDataSource> { AuthRemoteDataSourceMockImpl() }
    single<PictureRemoteDataSource> { PictureRemoteDataSourceMockImpl(get()) }
    single<ProfileRemoteDataSource> { ProfileRemoteDataSourceMockImpl() }
}

val dataModule = module {
    includes(repositoryModule)
    includes(if(BuildConfig.BUILD_TYPE == "mock") mockSourceModule else sourceModule)
}