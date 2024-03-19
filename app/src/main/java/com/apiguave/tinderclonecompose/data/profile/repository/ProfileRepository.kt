package com.apiguave.tinderclonecompose.data.profile.repository

import com.apiguave.tinderclonecompose.data.picture.Picture

interface ProfileRepository {
    suspend fun createProfile(profile: CreateUserProfile)
    suspend fun updateProfile(bio: String, gender: Gender, orientation: Orientation, pictures: List<Picture>): UserProfile
    suspend fun getProfile(): UserProfile

    val isUserSignedIn: Boolean
    suspend fun signIn(account: Account)
    suspend fun signUp(account: Account)
    fun signOut()
}