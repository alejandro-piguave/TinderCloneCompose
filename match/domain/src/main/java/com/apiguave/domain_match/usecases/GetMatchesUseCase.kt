package com.apiguave.domain_match.usecases

import com.apiguave.domain_match.model.Match
import com.apiguave.domain_match.repository.MatchRepository

class GetMatchesUseCase(private val matchRepository: MatchRepository) {

    suspend operator fun invoke(): Result<List<Match>> {
        return Result.runCatching { matchRepository.getMatches() }
    }
}