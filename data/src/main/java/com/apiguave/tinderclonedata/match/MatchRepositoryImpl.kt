package com.apiguave.tinderclonedata.match

import com.apiguave.tinderclonedomain.match.Match
import com.apiguave.tinderclonedomain.match.MatchRepository

class MatchRepositoryImpl(
    private val matchRemoteDataSource: MatchRemoteDataSource
): MatchRepository {

    override suspend fun getMatches(): List<Match> = matchRemoteDataSource.getMatches()

    override suspend fun getMatch(id: String): Match {
        return matchRemoteDataSource.getMatch(id)
    }

}