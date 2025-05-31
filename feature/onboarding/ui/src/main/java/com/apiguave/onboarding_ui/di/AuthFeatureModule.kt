package com.apiguave.onboarding_ui.di

import com.apiguave.onboarding_ui.login.LoginViewModel
import com.apiguave.onboarding_ui.create_profile.CreateProfileViewModel
import com.apiguave.onboarding_ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authFeatureModule = module {
    //View models
    viewModel { CreateProfileViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { SplashViewModel(get()) }
}