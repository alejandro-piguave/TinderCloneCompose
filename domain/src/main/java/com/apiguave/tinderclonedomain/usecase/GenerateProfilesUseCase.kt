package com.apiguave.tinderclonedomain.usecase

import com.apiguave.tinderclonedomain.profile.ProfileGenerator
import com.apiguave.tinderclonedomain.profile.ProfileRepository

class GenerateProfilesUseCase(
    private val profileGenerator: ProfileGenerator,
    private val profileRepository: ProfileRepository) {

    suspend operator fun invoke(amount: Int): Result<Unit> {
        return Result.runCatching {
            val profiles = profileGenerator.generateProfiles(amount)
            profiles.forEach {
                profileRepository.createProfile(it)
            }
        }
    }
}