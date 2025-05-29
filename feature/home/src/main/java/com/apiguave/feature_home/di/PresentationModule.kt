package com.apiguave.feature_home.di

import com.apiguave.feature_home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    //View models
    viewModel { HomeViewModel(get(), get(), get(), get(), get()) }
}