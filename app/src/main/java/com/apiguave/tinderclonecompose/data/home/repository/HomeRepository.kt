package com.apiguave.tinderclonecompose.data.home.repository

interface HomeRepository {
    suspend fun likeProfile(profile: Profile): NewMatch?
    suspend fun passProfile(profile: Profile)
    suspend fun getProfiles(): List<Profile>
}