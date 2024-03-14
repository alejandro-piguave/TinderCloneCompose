package com.apiguave.tinderclonecompose.data.home

import com.apiguave.tinderclonecompose.data.home.entity.NewMatch
import com.apiguave.tinderclonecompose.data.home.entity.Profile

interface HomeRepository {
    suspend fun likeProfile(profile: Profile): NewMatch?
    suspend fun passProfile(profile: Profile)
    suspend fun getProfiles(): List<Profile>
}