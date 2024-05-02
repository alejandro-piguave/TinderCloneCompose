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
    factory { SignUpUseCase(get(), get()) }
    factory { GetMaxBirthdateUseCase() }
    factory { GenerateProfilesUseCase(get(), get()) }
    factory { GetProfileUseCase(get()) }
    factory { LikeProfileUseCase(get(), get()) }
    factory { PassProfileUseCase(get()) }
    factory { SendMessageUseCase(get()) }
    factory { GetMessagesUseCase(get()) }
    factory { SignInUseCase(get()) }
    factory { IsUserSignedInUseCase(get()) }
    factory { GetMatchesUseCase(get()) }
    factory { SignOutUseCase(get()) }
    factory { UpdateProfileUseCase(get()) }
    factory { GetProfilesUseCase(get()) }
}