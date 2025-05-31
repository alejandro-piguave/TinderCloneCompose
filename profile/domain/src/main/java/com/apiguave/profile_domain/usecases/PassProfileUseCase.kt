package com.apiguave.profile_domain.usecases

import com.apiguave.profile_domain.model.Profile
import com.apiguave.profile_domain.repository.ProfileRepository

class PassProfileUseCase(private val profileRepository: ProfileRepository) {

    suspend operator fun invoke(profile: Profile): Result<Unit> {
        return Result.runCatching { profileRepository.passProfile(profile) }
    }
}