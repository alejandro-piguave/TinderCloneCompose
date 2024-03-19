package com.apiguave.tinderclonecompose.data.di

import com.apiguave.tinderclonecompose.data.api.match.MatchApi
import com.apiguave.tinderclonecompose.data.api.picture.PictureApi
import com.apiguave.tinderclonecompose.data.api.user.UserApi
import com.apiguave.tinderclonecompose.data.auth.AuthRemoteDataSource
import com.apiguave.tinderclonecompose.data.picture.datasource.PictureRemoteDataSource
import com.apiguave.tinderclonecompose.data.impl.AuthRepositoryImpl
import com.apiguave.tinderclonecompose.data.impl.HomeRepositoryImpl
import com.apiguave.tinderclonecompose.data.impl.MatchRepositoryImpl
import com.apiguave.tinderclonecompose.data.impl.MessageRepositoryImpl
import com.apiguave.tinderclonecompose.data.impl.ProfileRepositoryImpl
import com.apiguave.tinderclonecompose.data.auth.AuthRepository
import com.apiguave.tinderclonecompose.data.home.datasource.HomeRemoteDataSource
import com.apiguave.tinderclonecompose.data.match.repository.MatchRepository
import com.apiguave.tinderclonecompose.data.message.repository.MessageRepository
import com.apiguave.tinderclonecompose.data.profile.repository.ProfileRepository
import com.apiguave.tinderclonecompose.data.home.repository.HomeRepository
import com.apiguave.tinderclonecompose.data.impl.PictureRepositoryImpl
import com.apiguave.tinderclonecompose.data.impl.UserRepositoryImpl
import com.apiguave.tinderclonecompose.data.match.datasource.MatchRemoteDataSource
import com.apiguave.tinderclonecompose.data.message.datasource.MessageRemoteDataSource
import com.apiguave.tinderclonecompose.data.picture.datasource.PictureLocalDataSource
import com.apiguave.tinderclonecompose.data.picture.repository.PictureRepository
import com.apiguave.tinderclonecompose.data.user.datasource.UserLocalDataSource
import com.apiguave.tinderclonecompose.data.user.datasource.UserRemoteDataSource
import com.apiguave.tinderclonecompose.data.user.repository.UserRepository
import org.koin.dsl.module

val dataModule = module {

    //Api
    single { MatchApi() }
    single { UserApi() }
    single { PictureApi() }

    //Auth
    single { AuthRemoteDataSource() }
    single<AuthRepository> { AuthRepositoryImpl(get()) }

    //Home
    single { HomeRemoteDataSource(get(), get()) }
    single<HomeRepository> { HomeRepositoryImpl(get(), get(), get()) }

    //Profile
    single<ProfileRepository> { ProfileRepositoryImpl(get(), get(),get()) }

    //Messages
    single { MessageRemoteDataSource() }
    single<MessageRepository> { MessageRepositoryImpl(get()) }

    //Users
    single { UserRemoteDataSource() }
    single { UserLocalDataSource() }
    single<UserRepository> { UserRepositoryImpl(get(), get(), get()) }

    //Pictures
    single { PictureRemoteDataSource() }
    single { PictureLocalDataSource() }
    single<PictureRepository> { PictureRepositoryImpl(get(), get(), get(), get()) }

    //Matches
    single { MatchRemoteDataSource(get(), get(), get()) }
    single<MatchRepository> { MatchRepositoryImpl(get()) }
}