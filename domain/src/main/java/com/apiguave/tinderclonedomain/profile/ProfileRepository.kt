package com.apiguave.tinderclonedomain.profile

import java.time.LocalDate

interface ProfileRepository {
    suspend fun addProfile(
        userId: String,
        name: String,
        birthdate: LocalDate,
        bio: String,
        gender: Gender,
        orientation: Orientation,
        pictures: List<LocalPicture>
    )
    suspend fun updateProfile(bio: String, gender: Gender, orientation: Orientation, pictures: List<Picture>): UserProfile
    suspend fun getProfile(): UserProfile

    suspend fun likeProfile(profile: Profile): String?
    suspend fun passProfile(profile: Profile)
    suspend fun getProfiles(): List<Profile>

}