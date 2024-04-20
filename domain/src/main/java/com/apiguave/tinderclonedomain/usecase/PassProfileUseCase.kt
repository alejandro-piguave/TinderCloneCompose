package com.apiguave.tinderclonedomain.usecase

import com.apiguave.tinderclonedomain.profile.Profile
import com.apiguave.tinderclonedomain.profile.ProfileRepository

class PassProfileUseCase(private val profileRepository: ProfileRepository) {

    suspend operator fun invoke(profile: Profile): Result<Unit> {
        return Result.runCatching { profileRepository.passProfile(profile) }
    }
}