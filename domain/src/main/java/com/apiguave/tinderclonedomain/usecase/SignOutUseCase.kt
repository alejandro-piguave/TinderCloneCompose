package com.apiguave.tinderclonedomain.usecase

import com.apiguave.tinderclonedomain.auth.AuthRepository

class SignOutUseCase(private val authRepository: AuthRepository) {
    operator fun invoke(): Result<Unit> {
        return Result.runCatching {
            authRepository.signOut()
        }
    }
}