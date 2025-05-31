package com.apiguave.auth_domain.usecases

import com.apiguave.auth_domain.repository.AuthRepository


class SignOutUseCase(private val authRepository: AuthRepository) {
    operator fun invoke(): Result<Unit> {
        return Result.runCatching {
            authRepository.signOut()
        }
    }
}