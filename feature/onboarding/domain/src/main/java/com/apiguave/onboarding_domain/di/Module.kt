package com.apiguave.onboarding_domain.di

import com.apiguave.onboarding_domain.ResolveStartDestinationUseCase
import com.apiguave.onboarding_domain.CreateProfileUseCase
import org.koin.dsl.module

val onboardingDomainModule = module {
    factory { CreateProfileUseCase(get(), get(), get()) }
    factory { ResolveStartDestinationUseCase(get(), get()) }
}