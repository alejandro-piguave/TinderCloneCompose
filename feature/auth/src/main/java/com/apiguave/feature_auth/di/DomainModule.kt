package com.apiguave.feature_auth.di

import com.apiguave.domain_auth.usecases.GetMaxBirthdateUseCase
import com.apiguave.domain_auth.usecases.IsUserSignedInUseCase
import com.apiguave.domain_auth.usecases.SignInUseCase
import com.apiguave.domain_auth.usecases.SignOutUseCase
import com.apiguave.tinderclonedomain.usecase.GetProfileUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetMaxBirthdateUseCase() }
    factory { GetProfileUseCase(get()) }
    factory { SignInUseCase(get()) }
    factory { IsUserSignedInUseCase(get()) }
    factory { SignOutUseCase(get()) }
}