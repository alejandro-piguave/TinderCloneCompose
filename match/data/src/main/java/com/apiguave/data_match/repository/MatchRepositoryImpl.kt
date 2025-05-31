package com.apiguave.data_match.repository

import com.apiguave.domain_match.model.Match
import com.apiguave.domain_match.repository.MatchRepository


class MatchRepositoryImpl(
    private val matchRemoteDataSource: MatchRemoteDataSource
): MatchRepository {

    override suspend fun getMatches(): List<Match> = matchRemoteDataSource.getMatches()

    override suspend fun getMatch(id: String): Match {
        return matchRemoteDataSource.getMatch(id)
    }

}