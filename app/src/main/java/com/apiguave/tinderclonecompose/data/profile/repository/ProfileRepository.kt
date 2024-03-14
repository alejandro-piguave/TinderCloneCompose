package com.apiguave.tinderclonecompose.data.profile.repository

import com.apiguave.tinderclonecompose.data.picture.repository.Picture

interface ProfileRepository {
    suspend fun createProfile(profile: CreateUserProfile)
    suspend fun createProfile(userId: String, profile: CreateUserProfile)
    suspend fun updateProfile(bio: String, gender: Gender, orientation: Orientation, pictures: List<Picture>): UserProfile
    suspend fun getProfile(): UserProfile
}