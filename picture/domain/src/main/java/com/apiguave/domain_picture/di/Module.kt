package com.apiguave.domain_picture.di

import com.apiguave.domain_picture.usecases.GetPictureUseCase
import org.koin.dsl.module

val domainPictureModule = module {
    factory { GetPictureUseCase(get()) }
}