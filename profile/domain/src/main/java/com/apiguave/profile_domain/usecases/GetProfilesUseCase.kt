package com.apiguave.profile_domain.usecases

import com.apiguave.profile_domain.model.Profile
import com.apiguave.profile_domain.repository.ProfileRepository

class GetProfilesUseCase(private val profileRepository: ProfileRepository) {

    suspend operator fun invoke(): Result<List<Profile>> {
       return Result.runCatching {
            profileRepository.getProfiles()
        }
    }
}