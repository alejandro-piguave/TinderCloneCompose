package com.apiguave.home_ui.usecases

import com.apiguave.match_domain.model.Match
import com.apiguave.match_domain.repository.MatchRepository
import com.apiguave.profile_domain.model.Profile
import com.apiguave.profile_domain.repository.ProfileRepository

class LikeProfileUseCase(private val profileRepository: ProfileRepository, private val matchRepository: MatchRepository) {

    suspend operator fun invoke(profile: Profile): Result<Match?> {
        return Result.runCatching {
            profileRepository.likeProfile(profile)?.let {
                matchRepository.getMatch(it)
            }
        }
    }
}