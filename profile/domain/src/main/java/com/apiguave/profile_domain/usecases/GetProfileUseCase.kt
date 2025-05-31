package com.apiguave.profile_domain.usecases

import com.apiguave.profile_domain.model.UserProfile
import com.apiguave.profile_domain.repository.ProfileRepository

class GetProfileUseCase(private val profileRepository: ProfileRepository) {

    suspend operator fun invoke(): Result<UserProfile> {
        return Result.runCatching { profileRepository.getProfile() }
    }
}