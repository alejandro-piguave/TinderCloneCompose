package com.apiguave.tinderclonedomain.match

interface MatchRepository {
    suspend fun getMatches(): List<Match>
    suspend fun getMatch(id: String): Match
}