package com.apiguave.domain_auth.usecases

import com.apiguave.domain_auth.model.Account
import com.apiguave.domain_auth.repository.AuthRepository

class SignInUseCase(private val authRepository: AuthRepository) {

    suspend operator fun invoke(account: Account): Result<Unit> {
        return Result.runCatching {
            authRepository.signIn(account)
        }
    }
}