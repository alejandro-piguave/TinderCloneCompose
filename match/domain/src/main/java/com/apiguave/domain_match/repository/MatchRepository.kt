package com.apiguave.domain_match.repository

import com.apiguave.domain_match.model.Match

interface MatchRepository {
    suspend fun getMatches(): List<Match>
    suspend fun getMatch(id: String): Match
}