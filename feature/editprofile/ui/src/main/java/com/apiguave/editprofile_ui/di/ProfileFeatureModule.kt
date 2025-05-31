package com.apiguave.editprofile_ui.di

import com.apiguave.editprofile_ui.EditProfileViewModel
import com.apiguave.editprofile_ui.usecases.UpdatePicturesUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val profileFeatureModule = module {
    //View models
    viewModel { EditProfileViewModel(get(), get(), get(), get(), get()) }

    //Feature use cases
    factory { UpdatePicturesUseCase(get(), get()) }
}