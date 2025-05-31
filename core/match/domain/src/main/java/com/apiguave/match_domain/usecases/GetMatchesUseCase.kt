package com.apiguave.match_domain.usecases

import com.apiguave.match_domain.model.Match
import com.apiguave.match_domain.repository.MatchRepository

class GetMatchesUseCase(private val matchRepository: MatchRepository) {

    suspend operator fun invoke(): Result<List<Match>> {
        return Result.runCatching { matchRepository.getMatches() }
    }
}