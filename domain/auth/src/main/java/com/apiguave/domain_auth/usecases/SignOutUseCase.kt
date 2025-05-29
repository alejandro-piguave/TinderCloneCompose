package com.apiguave.domain_auth.usecases

import com.apiguave.domain_auth.repository.AuthRepository


class SignOutUseCase(private val authRepository: AuthRepository) {
    operator fun invoke(): Result<Unit> {
        return Result.runCatching {
            authRepository.signOut()
        }
    }
}