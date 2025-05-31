package com.apiguave.match_data.repository

import com.apiguave.match_domain.model.Match


interface MatchRemoteDataSource {
    suspend fun getMatches(): List<Match>
    suspend fun getMatch(id: String): Match
}