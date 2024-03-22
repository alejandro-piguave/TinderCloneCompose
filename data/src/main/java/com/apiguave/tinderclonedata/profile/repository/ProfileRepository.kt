package com.apiguave.tinderclonedata.profile.repository

import com.apiguave.tinderclonedata.picture.Picture

interface ProfileRepository {
    suspend fun updateProfile(bio: String, gender: Gender, orientation: Orientation, pictures: List<Picture>): UserProfile
    suspend fun getProfile(): UserProfile

    val isUserSignedIn: Boolean
    suspend fun signIn(account: Account)
    suspend fun signUp(account: Account, profile: CreateUserProfile)
    fun signOut()
}