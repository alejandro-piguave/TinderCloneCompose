package com.apiguave.tinderclonecompose.di

import com.apiguave.tinderclonedomain.usecase.GetMatchesUseCase
import com.apiguave.tinderclonedomain.usecase.GetMessagesUseCase
import com.apiguave.tinderclonedomain.usecase.GetPictureUseCase
import com.apiguave.tinderclonedomain.usecase.GetProfileUseCase
import com.apiguave.tinderclonedomain.usecase.GetProfilesUseCase
import com.apiguave.tinderclonedomain.usecase.LikeProfileUseCase
import com.apiguave.tinderclonedomain.usecase.PassProfileUseCase
import com.apiguave.tinderclonedomain.usecase.SendMessageUseCase
import com.apiguave.tinderclonedomain.usecase.UpdatePicturesUseCase
import com.apiguave.tinderclonedomain.usecase.UpdateProfileUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetProfileUseCase(get()) }
    factory { LikeProfileUseCase(get(), get()) }
    factory { PassProfileUseCase(get()) }
    factory { SendMessageUseCase(get()) }
    factory { GetMessagesUseCase(get()) }
    factory { GetMatchesUseCase(get()) }
    factory { UpdateProfileUseCase(get()) }
    factory { GetProfilesUseCase(get()) }
    factory { GetPictureUseCase(get()) }
    factory { UpdatePicturesUseCase(get(), get()) }
}