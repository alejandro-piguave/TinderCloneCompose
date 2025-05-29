package com.apiguave.feature_home.usecases

import com.apiguave.domain_match.model.Match
import com.apiguave.domain_match.repository.MatchRepository
import com.apiguave.domain_profile.model.Profile
import com.apiguave.domain_profile.repository.ProfileRepository

class LikeProfileUseCase(private val profileRepository: ProfileRepository, private val matchRepository: MatchRepository) {

    suspend operator fun invoke(profile: Profile): Result<Match?> {
        return Result.runCatching {
            profileRepository.likeProfile(profile)?.let {
                matchRepository.getMatch(it)
            }
        }
    }
}