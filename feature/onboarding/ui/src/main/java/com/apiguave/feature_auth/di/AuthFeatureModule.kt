package com.apiguave.feature_auth.di

import com.apiguave.feature_auth.login.LoginViewModel
import com.apiguave.feature_auth.register.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authFeatureModule = module {
    //View models
    viewModel { SignUpViewModel(get()) }
    viewModel { LoginViewModel(get(), get()) }
}