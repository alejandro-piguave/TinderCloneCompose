package com.apiguave.tinderclonedomain.usecase

import com.apiguave.tinderclonedomain.auth.Account
import com.apiguave.tinderclonedomain.auth.AuthRepository

class SignInUseCase(private val authRepository: AuthRepository) {

    suspend operator fun invoke(account: Account): Result<Unit> {
        return try {
            authRepository.signIn(account)
            Result.success(Unit)
        } catch (e: Throwable) {
            if(authRepository.isUserSignedIn){
                authRepository.signOut()
            }
            Result.failure(e)
        }
    }
}