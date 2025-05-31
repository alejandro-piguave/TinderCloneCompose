package com.apiguave.auth_domain.usecases

import com.apiguave.auth_domain.repository.AuthRepository

class IsUserSignedInUseCase(private val authRepository: AuthRepository) {

    operator fun invoke(): Boolean = authRepository.userId != null
}