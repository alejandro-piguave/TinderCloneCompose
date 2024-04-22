package com.apiguave.tinderclonedomain.profile


interface ProfileGenerator {
    suspend fun generateProfile(): GeneratedProfile
    suspend fun generateProfiles(amount: Int): List<GeneratedProfile>
}