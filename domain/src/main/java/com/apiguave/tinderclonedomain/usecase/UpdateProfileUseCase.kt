package com.apiguave.tinderclonedomain.usecase

import com.apiguave.tinderclonedomain.profile.Gender
import com.apiguave.tinderclonedomain.profile.Orientation
import com.apiguave.tinderclonedomain.profile.ProfileRepository

class UpdateProfileUseCase(private val profileRepository: ProfileRepository) {

    suspend operator fun invoke(bio: String, gender: Gender, orientation: Orientation): Result<Unit> {
        return Result.runCatching {
            profileRepository.updateProfile(bio, gender, orientation)
        }
    }
}