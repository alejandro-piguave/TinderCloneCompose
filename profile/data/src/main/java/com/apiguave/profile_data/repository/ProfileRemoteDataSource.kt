package com.apiguave.profile_data.repository

import com.apiguave.profile_domain.model.Gender
import com.apiguave.profile_domain.model.Orientation
import com.apiguave.profile_domain.model.Profile
import com.apiguave.profile_domain.model.UserProfile
import java.time.LocalDate

interface ProfileRemoteDataSource {
    suspend fun hasUserProfile(): Boolean
    suspend fun getUserProfile(): UserProfile
    suspend fun createProfile(
        userId: String,
        name: String,
        birthdate: LocalDate,
        bio: String,
        gender: Gender,
        orientation: Orientation
    )
    suspend fun updateProfile(
        bio: String,
        gender: Gender,
        orientation: Orientation
    )
    suspend fun updateProfile(pictureNames: List<String>)

    suspend fun getProfiles(): List<Profile>
    suspend fun passProfile(profile: Profile)
    suspend fun likeProfile(profile: Profile): String?
}