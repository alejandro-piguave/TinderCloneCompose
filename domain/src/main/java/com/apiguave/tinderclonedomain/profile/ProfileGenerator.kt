package com.apiguave.tinderclonedomain.profile


interface ProfileGenerator {
    suspend fun generateProfile(): CreateUserProfile
    suspend fun generateProfiles(amount: Int): List<CreateUserProfile>
}