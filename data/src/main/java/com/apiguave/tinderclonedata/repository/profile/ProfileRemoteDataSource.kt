package com.apiguave.tinderclonedata.repository.profile

import com.apiguave.tinderclonedomain.profile.Gender
import com.apiguave.tinderclonedomain.profile.LocalPicture
import com.apiguave.tinderclonedomain.profile.Orientation
import com.apiguave.tinderclonedomain.profile.Picture
import com.apiguave.tinderclonedomain.profile.Profile
import com.apiguave.tinderclonedomain.profile.UserProfile
import java.time.LocalDate

interface ProfileRemoteDataSource {
    suspend fun getUserProfile(): UserProfile
    suspend fun createProfile(
        userId: String,
        name: String,
        birthdate: LocalDate,
        bio: String,
        gender: Gender,
        orientation: Orientation,
        pictures: List<LocalPicture>
    )
    suspend fun updateProfile(
        bio: String,
        gender: Gender,
        orientation: Orientation,
        pictures: List<Picture>
    ): UserProfile

    suspend fun getProfiles(): List<Profile>
    suspend fun passProfile(profile: Profile)
    suspend fun likeProfile(profile: Profile): String?
}