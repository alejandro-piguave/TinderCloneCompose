package com.apiguave.onboarding_domain.di

import com.apiguave.onboarding_domain.SignUpUseCase
import org.koin.dsl.module

val onboardingDomainModule = module {
    factory { SignUpUseCase(get(), get(), get()) }
}