package com.apiguave.tinderclonedomain.usecase

import com.apiguave.tinderclonedomain.auth.AuthRepository

class IsUserSignedInUseCase(private val authRepository: AuthRepository) {

    operator fun invoke(): Boolean = authRepository.isUserSignedIn
}