package com.apiguave.tinderclonedata.match.repository

interface MatchRepository {
    suspend fun getMatches(): List<Match>
}