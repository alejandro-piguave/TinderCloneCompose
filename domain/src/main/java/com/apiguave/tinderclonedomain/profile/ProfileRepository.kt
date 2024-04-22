package com.apiguave.tinderclonedomain.profile

import com.apiguave.tinderclonedomain.picture.Picture

interface ProfileRepository {
    suspend fun createProfile(createUserProfile: CreateUserProfile)
    suspend fun updateProfile(bio: String, gender: Gender, orientation: Orientation, pictures: List<Picture>): UserProfile
    suspend fun getProfile(): UserProfile

    suspend fun likeProfile(profile: Profile): NewMatch?
    suspend fun passProfile(profile: Profile)
    suspend fun getProfiles(): List<Profile>
}