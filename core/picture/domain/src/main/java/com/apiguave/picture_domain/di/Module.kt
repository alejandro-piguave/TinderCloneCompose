package com.apiguave.picture_domain.di

import com.apiguave.picture_domain.usecases.GetPictureUseCase
import org.koin.dsl.module

val domainPictureModule = module {
    factory { GetPictureUseCase(get()) }
}