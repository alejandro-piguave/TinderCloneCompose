package com.apiguave.tinderclonedata.profile.repository

import com.apiguave.tinderclonedata.picture.Picture

interface ProfileRepository {
    suspend fun createProfile(createUserProfile: CreateUserProfile)
    suspend fun updateProfile(bio: String, gender: Gender, orientation: Orientation, pictures: List<Picture>): UserProfile
    suspend fun getProfile(): UserProfile
}