package com.apiguave.tinderclonecompose.data.match

import com.apiguave.tinderclonecompose.data.match.entity.Match

interface MatchRepository {
    suspend fun getMatches(): List<Match>
}