package com.apiguave.domain_profile.repository

import com.apiguave.domain_profile.model.Gender
import com.apiguave.domain_profile.model.Orientation
import com.apiguave.domain_profile.model.Profile
import com.apiguave.domain_profile.model.UserProfile
import java.time.LocalDate

interface ProfileRepository {
    suspend fun addProfile(
        userId: String,
        name: String,
        birthdate: LocalDate,
        bio: String,
        gender: Gender,
        orientation: Orientation
    )
    suspend fun updateProfile(bio: String, gender: Gender, orientation: Orientation)
    suspend fun updatePictures(pictureNames: List<String>)
    suspend fun getProfile(): UserProfile
    suspend fun hasProfile(userId: String): Boolean

    suspend fun likeProfile(profile: Profile): String?
    suspend fun passProfile(profile: Profile)
    suspend fun getProfiles(): List<Profile>

}