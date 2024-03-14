package com.apiguave.tinderclonecompose.data.di

import com.apiguave.tinderclonecompose.data.account.AuthRemoteDataSource
import com.apiguave.tinderclonecompose.data.datasource.FirestoreRemoteDataSource
import com.apiguave.tinderclonecompose.data.picture.datasource.PictureRemoteDataSource
import com.apiguave.tinderclonecompose.data.impl.AuthRepositoryImpl
import com.apiguave.tinderclonecompose.data.impl.HomeRepositoryImpl
import com.apiguave.tinderclonecompose.data.impl.MatchRepositoryImpl
import com.apiguave.tinderclonecompose.data.impl.MessageRepositoryImpl
import com.apiguave.tinderclonecompose.data.impl.ProfileRepositoryImpl
import com.apiguave.tinderclonecompose.data.account.AuthRepository
import com.apiguave.tinderclonecompose.data.match.MatchRepository
import com.apiguave.tinderclonecompose.data.message.repository.MessageRepository
import com.apiguave.tinderclonecompose.data.profile.repository.ProfileRepository
import com.apiguave.tinderclonecompose.data.home.HomeRepository
import com.apiguave.tinderclonecompose.data.impl.PictureRepositoryImpl
import com.apiguave.tinderclonecompose.data.impl.UserRepositoryImpl
import com.apiguave.tinderclonecompose.data.message.datasource.MessageRemoteDataSource
import com.apiguave.tinderclonecompose.data.picture.datasource.PictureLocalDataSource
import com.apiguave.tinderclonecompose.data.picture.repository.PictureRepository
import com.apiguave.tinderclonecompose.data.user.datasource.UserLocalDataSource
import com.apiguave.tinderclonecompose.data.user.datasource.UserRemoteDataSource
import com.apiguave.tinderclonecompose.data.user.repository.UserRepository
import org.koin.dsl.module

val dataModule = module {

    //Data sources
    single { AuthRemoteDataSource() }
    single { FirestoreRemoteDataSource() }

    //Repositories
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<MatchRepository> { MatchRepositoryImpl(get(),get(),get()) }
    single<HomeRepository> { HomeRepositoryImpl(get(), get(), get()) }
    single<ProfileRepository> { ProfileRepositoryImpl(get(),get(), get(), get()) }

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
}