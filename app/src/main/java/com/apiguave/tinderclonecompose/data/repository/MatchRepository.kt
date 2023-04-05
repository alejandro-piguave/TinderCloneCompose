package com.apiguave.tinderclonecompose.data.repository

import com.apiguave.tinderclonecompose.data.repository.model.Match

interface MatchRepository {
    suspend fun getMatches(): List<Match>
}