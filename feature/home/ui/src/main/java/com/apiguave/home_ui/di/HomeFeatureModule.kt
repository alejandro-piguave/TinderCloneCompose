package com.apiguave.home_ui.di

import com.apiguave.home_ui.HomeViewModel
import com.apiguave.home_ui.usecases.LikeProfileUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeFeatureModule = module {
    //View models
    viewModel { HomeViewModel(get(), get(), get(), get(), get()) }

    factory { LikeProfileUseCase(get(), get()) }
}