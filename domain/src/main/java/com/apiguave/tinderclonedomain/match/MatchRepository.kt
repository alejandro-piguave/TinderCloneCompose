package com.apiguave.tinderclonedomain.match

interface MatchRepository {
    suspend fun getMatches(): List<Match>
}