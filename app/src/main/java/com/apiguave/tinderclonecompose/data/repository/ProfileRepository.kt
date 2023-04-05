package com.apiguave.tinderclonecompose.data.repository

import com.apiguave.tinderclonecompose.data.repository.model.CreateUserProfile
import com.apiguave.tinderclonecompose.data.repository.model.CurrentProfile
import com.apiguave.tinderclonecompose.data.repository.model.UserPicture

interface ProfileRepository {
    suspend fun createUserProfile(profile: CreateUserProfile)
    suspend fun createUserProfile(userId: String, profile: CreateUserProfile)
    suspend fun updateProfile(currentProfile: CurrentProfile, newBio: String, newGenderIndex: Int, newOrientationIndex: Int, newPictures: List<UserPicture>): CurrentProfile
}