package com.apiguave.feature_profile.di

import com.apiguave.feature_profile.EditProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    //View models
    viewModel { EditProfileViewModel(get(), get(), get(), get(), get()) }
}