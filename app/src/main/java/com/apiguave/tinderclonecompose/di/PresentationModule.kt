package com.apiguave.tinderclonecompose.di

import com.apiguave.tinderclonecompose.chat.ChatViewModel
import com.apiguave.tinderclonecompose.editprofile.EditProfileViewModel
import com.apiguave.tinderclonecompose.home.HomeViewModel
import com.apiguave.tinderclonecompose.login.LoginViewModel
import com.apiguave.tinderclonecompose.matchlist.MatchListViewModel
import com.apiguave.tinderclonecompose.signup.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    //View models
    viewModel { ChatViewModel(get(), get()) }
    viewModel { EditProfileViewModel(get(), get(), get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { MatchListViewModel(get()) }
    viewModel { HomeViewModel(get(), get(), get(), get(), get()) }
}