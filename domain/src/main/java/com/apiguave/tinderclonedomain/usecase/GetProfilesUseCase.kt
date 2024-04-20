package com.apiguave.tinderclonedomain.usecase

import com.apiguave.tinderclonedomain.profile.Profile
import com.apiguave.tinderclonedomain.profile.ProfileRepository

class GetProfilesUseCase(private val profileRepository: ProfileRepository) {

    suspend operator fun invoke(): Result<List<Profile>> {
       return Result.runCatching {
            profileRepository.getProfiles()
        }
    }
}