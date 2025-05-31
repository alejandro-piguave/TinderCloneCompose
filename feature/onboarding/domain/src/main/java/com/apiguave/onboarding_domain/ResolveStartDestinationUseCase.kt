package com.apiguave.onboarding_domain

import com.apiguave.domain_auth.repository.AuthRepository
import com.apiguave.domain_profile.repository.ProfileRepository

enum class StartDestination {
    SignIn,
    CreateProfile,
    Home
}

class ResolveStartDestinationUseCase(
    private val profileRepository: ProfileRepository,
    private val authRepository: AuthRepository) {

    suspend operator fun invoke(): Result<StartDestination>  = try {
        val userId = authRepository.userId
        if (userId == null) {
            Result.success(StartDestination.SignIn)
        } else if (profileRepository.hasProfile(userId)) {
            Result.success(StartDestination.Home)
        } else {
            Result.success(StartDestination.CreateProfile)
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}