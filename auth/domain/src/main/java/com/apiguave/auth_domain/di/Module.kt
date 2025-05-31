package com.apiguave.auth_domain.di

import com.apiguave.auth_domain.usecases.IsUserSignedInUseCase
import com.apiguave.auth_domain.usecases.SignInUseCase
import com.apiguave.auth_domain.usecases.SignOutUseCase
import org.koin.dsl.module

val domainAuthModule = module {
    factory { IsUserSignedInUseCase(get()) }
    factory { SignInUseCase(get()) }
    factory { SignOutUseCase(get()) }
}