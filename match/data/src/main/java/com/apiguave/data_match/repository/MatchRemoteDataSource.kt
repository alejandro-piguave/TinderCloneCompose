package com.apiguave.data_match.repository

import com.apiguave.domain_match.model.Match


interface MatchRemoteDataSource {
    suspend fun getMatches(): List<Match>
    suspend fun getMatch(id: String): Match
}