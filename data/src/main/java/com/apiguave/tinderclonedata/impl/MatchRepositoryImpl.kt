package com.apiguave.tinderclonedata.impl

import com.apiguave.tinderclonedata.match.datasource.MatchRemoteDataSource
import com.apiguave.tinderclonedata.match.repository.Match
import com.apiguave.tinderclonedata.match.repository.MatchRepository

class MatchRepositoryImpl(
    private val matchRemoteDataSource: MatchRemoteDataSource
): MatchRepository {

    override suspend fun getMatches(): List<Match> = matchRemoteDataSource.getMatches()

}