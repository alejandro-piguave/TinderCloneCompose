package com.apiguave.domain_profile.usecases

import com.apiguave.domain_profile.model.UserProfile
import com.apiguave.domain_profile.repository.ProfileRepository

class GetProfileUseCase(private val profileRepository: ProfileRepository) {

    suspend operator fun invoke(): Result<UserProfile> {
        return Result.runCatching { profileRepository.getProfile() }
    }
}