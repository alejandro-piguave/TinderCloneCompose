package com.apiguave.tinderclonecompose.data.impl

import com.apiguave.tinderclonecompose.data.match.datasource.MatchRemoteDataSource
import com.apiguave.tinderclonecompose.data.match.repository.Match
import com.apiguave.tinderclonecompose.data.match.repository.MatchRepository

class MatchRepositoryImpl(
    private val matchRemoteDataSource: MatchRemoteDataSource
): MatchRepository {

    override suspend fun getMatches(): List<Match> = matchRemoteDataSource.getMatches()

}