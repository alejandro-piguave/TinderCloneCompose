package com.apiguave.tinderclonedata.profile.generator

import com.apiguave.tinderclonedata.profile.model.CreateUserProfile

interface ProfileGenerator {
    suspend fun generateProfile(): CreateUserProfile
    suspend fun generateProfiles(amount: Int): List<CreateUserProfile>
}