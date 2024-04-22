package com.apiguave.tinderclonedomain.usecase

import com.apiguave.tinderclonedomain.picture.PictureRepository
import com.apiguave.tinderclonedomain.profile.ProfileRepository
import com.apiguave.tinderclonedomain.profile.UserProfile

class GetProfileUseCase(private val profileRepository: ProfileRepository, private val pictureRepository: PictureRepository) {

    suspend operator fun invoke(): Result<UserProfile> {
        return Result.runCatching { profileRepository.getProfile() }
    }
}