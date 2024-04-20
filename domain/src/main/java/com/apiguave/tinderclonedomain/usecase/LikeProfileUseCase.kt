package com.apiguave.tinderclonedomain.usecase

import com.apiguave.tinderclonedomain.profile.NewMatch
import com.apiguave.tinderclonedomain.profile.Profile
import com.apiguave.tinderclonedomain.profile.ProfileRepository

class LikeProfileUseCase(private val profileRepository: ProfileRepository) {

    suspend operator fun invoke(profile: Profile): Result<NewMatch?> {
        return Result.runCatching { profileRepository.likeProfile(profile) }
    }
}