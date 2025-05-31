package com.apiguave.feature_home.di

import com.apiguave.feature_home.HomeViewModel
import com.apiguave.feature_home.usecases.LikeProfileUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeFeatureModule = module {
    //View models
    viewModel { HomeViewModel(get(), get(), get(), get(), get()) }

    factory { LikeProfileUseCase(get(), get()) }
}