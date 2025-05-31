package com.apiguave.feature_auth.di

import com.apiguave.feature_auth.login.LoginViewModel
import com.apiguave.feature_auth.create_profile.CreateProfileViewModel
import com.apiguave.feature_auth.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authFeatureModule = module {
    //View models
    viewModel { CreateProfileViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { SplashViewModel(get()) }
}