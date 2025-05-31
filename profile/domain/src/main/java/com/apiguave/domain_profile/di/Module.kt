package com.apiguave.domain_profile.di

import com.apiguave.domain_profile.usecases.GetProfileUseCase
import com.apiguave.domain_profile.usecases.GetProfilesUseCase
import com.apiguave.domain_profile.usecases.PassProfileUseCase
import com.apiguave.domain_profile.usecases.UpdateProfileUseCase
import org.koin.dsl.module

val domainProfileModule = module {
    factory { GetProfilesUseCase(get()) }
    factory { GetProfileUseCase(get()) }
    factory { PassProfileUseCase(get()) }
    factory { UpdateProfileUseCase(get()) }
}