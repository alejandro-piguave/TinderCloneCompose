package com.apiguave.tinderclonedomain.usecase

import com.apiguave.tinderclonedomain.profile.Gender
import com.apiguave.tinderclonedomain.profile.Orientation
import com.apiguave.tinderclonedomain.profile.Picture
import com.apiguave.tinderclonedomain.profile.ProfileRepository
import com.apiguave.tinderclonedomain.profile.UserProfile

class UpdateProfileUseCase(private val profileRepository: ProfileRepository) {

    suspend operator fun invoke(bio: String, gender: Gender, orientation: Orientation, pictures: List<Picture>): Result<UserProfile> {
        return Result.runCatching {
            profileRepository.updateProfile(bio, gender, orientation, pictures)
        }
    }
}