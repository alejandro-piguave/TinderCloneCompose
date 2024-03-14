package com.apiguave.tinderclonecompose.domain.home

import com.apiguave.tinderclonecompose.domain.home.entity.NewMatch
import com.apiguave.tinderclonecompose.domain.home.entity.Profile

interface HomeRepository {
    suspend fun swipeUser(profile: Profile, isLike: Boolean): NewMatch?
    suspend fun getProfiles(): List<Profile>
}