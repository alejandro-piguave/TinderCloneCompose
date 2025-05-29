package com.apiguave.domain_profile.usecases

import com.apiguave.domain_profile.model.Profile
import com.apiguave.domain_profile.repository.ProfileRepository

class PassProfileUseCase(private val profileRepository: ProfileRepository) {

    suspend operator fun invoke(profile: Profile): Result<Unit> {
        return Result.runCatching { profileRepository.passProfile(profile) }
    }
}