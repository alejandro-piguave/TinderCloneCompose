package com.apiguave.tinderclonecompose.di

import com.apiguave.tinderclonecompose.data.datasource.AuthRemoteDataSource
import com.apiguave.tinderclonecompose.data.datasource.FirestoreRemoteDataSource
import com.apiguave.tinderclonecompose.data.datasource.StorageRemoteDataSource
import com.apiguave.tinderclonecompose.data.repository.*
import com.apiguave.tinderclonecompose.data.repository.impl.*
import com.apiguave.tinderclonecompose.ui.chat.ChatViewModel
import com.apiguave.tinderclonecompose.ui.editprofile.EditProfileViewModel
import com.apiguave.tinderclonecompose.ui.home.HomeViewModel
import com.apiguave.tinderclonecompose.ui.login.LoginViewModel
import com.apiguave.tinderclonecompose.ui.matchlist.MatchListViewModel
import com.apiguave.tinderclonecompose.ui.newmatch.NewMatchViewModel
import com.apiguave.tinderclonecompose.ui.signup.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    //Data sources
    single { AuthRemoteDataSource() }
    single { FirestoreRemoteDataSource() }
    single { StorageRemoteDataSource() }

    //Repositories
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<MatchRepository> { MatchRepositoryImpl(get(),get(),get()) }
    single<MessageRepository> { MessageRepositoryImpl(get()) }
    single<ProfileCardRepository> { ProfileCardRepositoryImpl(get(), get()) }
    single<ProfileRepository> { ProfileRepositoryImpl(get(),get(),get()) }

    //View models
    viewModel { ChatViewModel(get()) }
    viewModel { NewMatchViewModel(get()) }
    viewModel { EditProfileViewModel(get(), get()) }
    viewModel { SignUpViewModel(get(), get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { MatchListViewModel(get()) }
}