package com.apiguave.domain_auth.usecases

import com.apiguave.domain_auth.repository.AuthRepository

class IsUserSignedInUseCase(private val authRepository: AuthRepository) {

    operator fun invoke(): Boolean = authRepository.userId != null
}