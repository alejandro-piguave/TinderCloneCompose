package com.apiguave.tinderclonedomain.usecase

import com.apiguave.tinderclonedomain.match.Match
import com.apiguave.tinderclonedomain.match.MatchRepository
import com.apiguave.tinderclonedomain.profile.Profile
import com.apiguave.tinderclonedomain.profile.ProfileRepository

class LikeProfileUseCase(private val profileRepository: ProfileRepository, private val matchRepository: MatchRepository) {

    suspend operator fun invoke(profile: Profile): Result<Match?> {
        return Result.runCatching {
            profileRepository.likeProfile(profile)?.let {
                matchRepository.getMatch(it)
            }
        }
    }
}