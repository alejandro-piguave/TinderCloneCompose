package com.apiguave.tinderclonecompose.di

import com.apiguave.tinderclonedomain.usecase.GenerateProfilesUseCase
import com.apiguave.tinderclonedomain.usecase.GetMessagesUseCase
import com.apiguave.tinderclonedomain.usecase.GetProfilesUseCase
import com.apiguave.tinderclonedomain.usecase.IsUserSignedInUseCase
import com.apiguave.tinderclonedomain.usecase.LikeProfileUseCase
import com.apiguave.tinderclonedomain.usecase.PassProfileUseCase
import com.apiguave.tinderclonedomain.usecase.SendMessageUseCase
import com.apiguave.tinderclonedomain.usecase.SignInUseCase
import com.apiguave.tinderclonedomain.usecase.SignUpUseCase
import org.koin.dsl.module

val domainModule = module {
    single { SignUpUseCase(get(), get()) }
    single { GenerateProfilesUseCase(get(), get()) }
    single { GetProfilesUseCase(get()) }
    single { LikeProfileUseCase(get()) }
    single { PassProfileUseCase(get()) }
    single { SendMessageUseCase(get()) }
    single { GetMessagesUseCase(get()) }
    single { SignInUseCase(get()) }
    single { IsUserSignedInUseCase(get()) }
}