package com.apiguave.profile_domain.usecases

import com.apiguave.profile_domain.model.Gender
import com.apiguave.profile_domain.model.Orientation
import com.apiguave.profile_domain.repository.ProfileRepository

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