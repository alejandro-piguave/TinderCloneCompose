package com.apiguave.tinderclonecompose.domain.match

import com.apiguave.tinderclonecompose.domain.match.entity.Match

interface MatchRepository {
    suspend fun getMatches(): List<Match>
}