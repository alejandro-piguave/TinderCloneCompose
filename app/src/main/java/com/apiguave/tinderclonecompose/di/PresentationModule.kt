package com.apiguave.tinderclonecompose.di

import com.apiguave.tinderclonecompose.editprofile.EditProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    //View models
    viewModel { EditProfileViewModel(get(), get(), get(), get(), get()) }
}