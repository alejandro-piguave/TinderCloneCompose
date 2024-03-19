package com.apiguave.tinderclonecompose.presentation.di

import com.apiguave.tinderclonecompose.presentation.chat.ChatViewModel
import com.apiguave.tinderclonecompose.presentation.editprofile.EditProfileViewModel
import com.apiguave.tinderclonecompose.presentation.home.HomeViewModel
import com.apiguave.tinderclonecompose.presentation.login.LoginViewModel
import com.apiguave.tinderclonecompose.presentation.matchlist.MatchListViewModel
import com.apiguave.tinderclonecompose.presentation.signup.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    //View models
    viewModel { ChatViewModel(get()) }
    viewModel { EditProfileViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { MatchListViewModel(get()) }
    viewModel { HomeViewModel(get(), get()) }
}