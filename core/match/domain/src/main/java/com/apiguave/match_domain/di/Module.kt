package com.apiguave.match_domain.di

import com.apiguave.match_domain.usecases.GetMatchesUseCase
import org.koin.dsl.module

val domainMatchModule = module {
    factory { GetMatchesUseCase(get()) }
}