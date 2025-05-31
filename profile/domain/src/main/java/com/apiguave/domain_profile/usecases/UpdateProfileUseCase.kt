package com.apiguave.domain_profile.usecases

import com.apiguave.domain_profile.model.Gender
import com.apiguave.domain_profile.model.Orientation
import com.apiguave.domain_profile.repository.ProfileRepository

class UpdateProfileUseCase(private val profileRepository: ProfileRepository) {

    suspend operator fun invoke(bio: String, gender: Gender, orientation: Orientation): Result<Unit> {
        return Result.runCatching {
            val profile = profileRepository.getProfile()
            if(profile.bio != bio || profile.gender != gender || profile.orientation != orientation){
                profileRepository.updateProfile(bio, gender, orientation)
            }
        }
    }
}