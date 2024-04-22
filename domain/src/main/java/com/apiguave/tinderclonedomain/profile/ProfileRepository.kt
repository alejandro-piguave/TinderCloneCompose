package com.apiguave.tinderclonedomain.profile

import com.apiguave.tinderclonedomain.picture.Picture
import java.time.LocalDate

interface ProfileRepository {
    suspend fun createProfile(
        id: String,
        name: String,
        birthdate: LocalDate,
        bio: String,
        gender: Gender,
        orientation: Orientation,
        pictures: List<String>
    )

    suspend fun updateProfile(
        bio: String,
        gender: Gender,
        orientation: Orientation,
        pictures: List<Picture>
    ): UserProfile

    suspend fun getProfile(): UserProfile

    suspend fun likeProfile(profile: Profile): NewMatch?
    suspend fun passProfile(profile: Profile)
    suspend fun getProfiles(): List<Profile>
}