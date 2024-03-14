package com.apiguave.tinderclonecompose.data.profile

import com.apiguave.tinderclonecompose.data.profile.entity.CreateUserProfile
import com.apiguave.tinderclonecompose.data.home.entity.UserProfile
import com.apiguave.tinderclonecompose.data.profile.entity.Gender
import com.apiguave.tinderclonecompose.data.profile.entity.Orientation
import com.apiguave.tinderclonecompose.data.profile.entity.UserPicture

interface ProfileRepository {
    suspend fun createUserProfile(profile: CreateUserProfile)
    suspend fun createUserProfile(userId: String, profile: CreateUserProfile)
    suspend fun updateProfile(bio: String, gender: Gender, orientation: Orientation, pictures: List<UserPicture>): UserProfile
    fun getUserProfile(): UserProfile
}