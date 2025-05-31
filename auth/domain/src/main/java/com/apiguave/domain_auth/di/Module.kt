package com.apiguave.domain_auth.di

import com.apiguave.domain_auth.usecases.IsUserSignedInUseCase
import com.apiguave.domain_auth.usecases.SignInUseCase
import com.apiguave.domain_auth.usecases.SignOutUseCase
import org.koin.dsl.module

val domainAuthModule = module {
    factory { IsUserSignedInUseCase(get()) }
    factory { SignInUseCase(get()) }
    factory { SignOutUseCase(get()) }
}