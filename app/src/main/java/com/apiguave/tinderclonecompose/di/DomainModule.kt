package com.apiguave.tinderclonecompose.di

import com.apiguave.tinderclonedomain.usecase.GenerateProfilesUseCase
import com.apiguave.tinderclonedomain.usecase.GetMatchesUseCase
import com.apiguave.tinderclonedomain.usecase.GetMaxBirthdateUseCase
import com.apiguave.tinderclonedomain.usecase.GetMessagesUseCase
import com.apiguave.tinderclonedomain.usecase.GetProfileUseCase
import com.apiguave.tinderclonedomain.usecase.GetProfilesUseCase
import com.apiguave.tinderclonedomain.usecase.IsUserSignedInUseCase
import com.apiguave.tinderclonedomain.usecase.LikeProfileUseCase
import com.apiguave.tinderclonedomain.usecase.PassProfileUseCase
import com.apiguave.tinderclonedomain.usecase.SendMessageUseCase
import com.apiguave.tinderclonedomain.usecase.SignInUseCase
import com.apiguave.tinderclonedomain.usecase.SignOutUseCase
import com.apiguave.tinderclonedomain.usecase.SignUpUseCase
import com.apiguave.tinderclonedomain.usecase.UpdateProfileUseCase
import org.koin.dsl.module

val domainModule = module {
    single { SignUpUseCase(get(), get()) }
    single { GetMaxBirthdateUseCase() }
    single { GenerateProfilesUseCase(get(), get()) }
    single { GetProfileUseCase(get()) }
    single { LikeProfileUseCase(get(), get()) }
    single { PassProfileUseCase(get()) }
    single { SendMessageUseCase(get()) }
    single { GetMessagesUseCase(get()) }
    single { SignInUseCase(get()) }
    single { IsUserSignedInUseCase(get()) }
    single { GetMatchesUseCase(get()) }
    single { SignOutUseCase(get()) }
    single { UpdateProfileUseCase(get()) }
    single { GetProfilesUseCase(get()) }
}