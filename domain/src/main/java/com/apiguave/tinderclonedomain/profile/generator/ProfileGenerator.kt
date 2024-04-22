package com.apiguave.tinderclonedomain.profile.generator


interface ProfileGenerator {
    suspend fun generateProfile(): GeneratedProfile
    suspend fun generateProfiles(amount: Int): List<GeneratedProfile>
}