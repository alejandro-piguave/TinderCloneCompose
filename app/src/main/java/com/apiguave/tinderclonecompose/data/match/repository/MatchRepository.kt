package com.apiguave.tinderclonecompose.data.match.repository

interface MatchRepository {
    suspend fun getMatches(): List<Match>
}