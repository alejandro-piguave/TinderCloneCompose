package com.apiguave.domain_match.di

import com.apiguave.domain_match.usecases.GetMatchesUseCase
import org.koin.dsl.module

val domainMatchModule = module {
    factory { GetMatchesUseCase(get()) }
}