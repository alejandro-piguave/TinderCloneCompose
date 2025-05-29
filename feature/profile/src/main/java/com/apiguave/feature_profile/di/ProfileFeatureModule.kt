package com.apiguave.feature_profile.di

import com.apiguave.feature_profile.EditProfileViewModel
import com.apiguave.feature_profile.usecases.UpdatePicturesUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val profileFeatureModule = module {
    //View models
    viewModel { EditProfileViewModel(get(), get(), get(), get(), get()) }

    //Feature use cases
    factory { UpdatePicturesUseCase(get(), get()) }
}