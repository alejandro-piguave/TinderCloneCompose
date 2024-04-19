package com.apiguave.tinderclonedata.profile.repository

import com.apiguave.tinderclonedata.profile.model.NewMatch
import com.apiguave.tinderclonedata.profile.model.Profile
import com.apiguave.tinderclonedata.picture.Picture
import com.apiguave.tinderclonedata.profile.model.CreateUserProfile
import com.apiguave.tinderclonedata.profile.model.Gender
import com.apiguave.tinderclonedata.profile.model.Orientation
import com.apiguave.tinderclonedata.profile.model.UserProfile

interface ProfileRepository {
    suspend fun createProfile(createUserProfile: CreateUserProfile)
    suspend fun updateProfile(bio: String, gender: Gender, orientation: Orientation, pictures: List<Picture>): UserProfile
    suspend fun getProfile(): UserProfile

    suspend fun likeProfile(profile: Profile): NewMatch?
    suspend fun passProfile(profile: Profile)
    suspend fun getProfiles(): List<Profile>
}