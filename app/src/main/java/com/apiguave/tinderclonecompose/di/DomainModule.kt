package com.apiguave.tinderclonecompose.di

import com.apiguave.tinderclonedomain.usecase.SignUpUseCase
import org.koin.dsl.module

val domainModule = module {
    single { SignUpUseCase(get(), get()) }
}