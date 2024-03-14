package com.apiguave.tinderclonecompose.domain.profile

import com.apiguave.tinderclonecompose.domain.profile.entity.CreateUserProfile
import com.apiguave.tinderclonecompose.domain.home.entity.UserProfile
import com.apiguave.tinderclonecompose.domain.profile.entity.Gender
import com.apiguave.tinderclonecompose.domain.profile.entity.Orientation
import com.apiguave.tinderclonecompose.domain.profile.entity.UserPicture

interface ProfileRepository {
    suspend fun createUserProfile(profile: CreateUserProfile)
    suspend fun createUserProfile(userId: String, profile: CreateUserProfile)
    suspend fun updateProfile(bio: String, gender: Gender, orientation: Orientation, pictures: List<UserPicture>): UserProfile
    fun getUserProfile(): UserProfile
}