package com.apiguave.match_domain.repository

import com.apiguave.match_domain.model.Match

interface MatchRepository {
    suspend fun getMatches(): List<Match>
    suspend fun getMatch(id: String): Match
}