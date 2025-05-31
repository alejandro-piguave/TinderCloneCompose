package com.apiguave.data_profile.repository

import com.apiguave.domain_profile.model.Gender
import com.apiguave.domain_profile.model.Orientation
import com.apiguave.domain_profile.model.Profile
import com.apiguave.domain_profile.model.UserProfile
import com.apiguave.domain_profile.repository.ProfileRepository
import java.time.LocalDate

class ProfileRepositoryImpl(
    private val profileRemoteDataSource: ProfileRemoteDataSource
): ProfileRepository {

    override suspend fun getProfile(): UserProfile {
        return profileRemoteDataSource.getUserProfile()
    }

    override suspend fun updateProfile(
        bio: String,
        gender: Gender,
        orientation: Orientation
    ) {
        profileRemoteDataSource.updateProfile(bio, gender, orientation)
    }

    override suspend fun updatePictures(pictureNames: List<String>) {
        profileRemoteDataSource.updateProfile(pictureNames)
    }

    override suspend fun addProfile(
        userId: String,
        name: String,
        birthdate: LocalDate,
        bio: String,
        gender: Gender,
        orientation: Orientation
    ) {
        profileRemoteDataSource.createProfile(userId, name, birthdate, bio, gender, orientation)
    }

    override suspend fun likeProfile(profile: Profile): String? {
        return profileRemoteDataSource.likeProfile(profile)
    }

    override suspend fun passProfile(profile: Profile) {
        profileRemoteDataSource.passProfile(profile)
    }

    override suspend fun getProfiles(): List<Profile> {
        return profileRemoteDataSource.getProfiles()
    }

}