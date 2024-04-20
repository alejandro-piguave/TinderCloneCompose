package com.apiguave.tinderclonedomain.usecase

import com.apiguave.tinderclonedomain.match.Match
import com.apiguave.tinderclonedomain.match.MatchRepository

class GetMatchesUseCase(private val matchRepository: MatchRepository) {

    suspend operator fun invoke(): Result<List<Match>> {
        return Result.runCatching { matchRepository.getMatches() }
    }
}