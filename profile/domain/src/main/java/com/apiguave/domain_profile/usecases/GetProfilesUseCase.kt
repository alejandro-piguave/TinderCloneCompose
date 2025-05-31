package com.apiguave.domain_profile.usecases

import com.apiguave.domain_profile.model.Profile
import com.apiguave.domain_profile.repository.ProfileRepository

class GetProfilesUseCase(private val profileRepository: ProfileRepository) {

    suspend operator fun invoke(): Result<List<Profile>> {
       return Result.runCatching {
            profileRepository.getProfiles()
        }
    }
}