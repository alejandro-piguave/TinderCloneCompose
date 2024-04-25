package com.apiguave.tinderclonedata.repository.match

import com.apiguave.tinderclonedomain.match.Match

interface MatchRemoteDataSource {
    suspend fun getMatches(): List<Match>
    suspend fun getMatch(id: String): Match
}