package com.apiguave.match_data.repository

import com.apiguave.match_domain.model.Match
import com.apiguave.match_domain.repository.MatchRepository


class MatchRepositoryImpl(
    private val matchRemoteDataSource: MatchFirebaseDataSource
): MatchRepository {

    override suspend fun getMatches(): List<Match> = matchRemoteDataSource.getMatches()

    override suspend fun getMatch(id: String): Match {
        return matchRemoteDataSource.getMatch(id)
    }

}