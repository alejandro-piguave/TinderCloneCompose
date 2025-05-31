package com.apiguave.profile_domain.di

import com.apiguave.profile_domain.usecases.GetProfileUseCase
import com.apiguave.profile_domain.usecases.GetProfilesUseCase
import com.apiguave.profile_domain.usecases.PassProfileUseCase
import com.apiguave.profile_domain.usecases.UpdateProfileUseCase
import org.koin.dsl.module

val domainProfileModule = module {
    factory { GetProfilesUseCase(get()) }
    factory { GetProfileUseCase(get()) }
    factory { PassProfileUseCase(get()) }
    factory { UpdateProfileUseCase(get()) }
}