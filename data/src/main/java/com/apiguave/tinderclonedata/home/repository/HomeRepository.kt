package com.apiguave.tinderclonedata.home.repository

interface HomeRepository {
    suspend fun likeProfile(profile: Profile): NewMatch?
    suspend fun passProfile(profile: Profile)
    suspend fun getProfiles(): List<Profile>
    suspend fun createRandomProfiles(amount: Int)
}