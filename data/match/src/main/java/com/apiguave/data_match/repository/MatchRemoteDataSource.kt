package com.apiguave.data_match.repository

import com.apiguave.tinderclonedomain.match.Match

interface MatchRemoteDataSource {
    suspend fun getMatches(): List<Match>
    suspend fun getMatch(id: String): Match
}