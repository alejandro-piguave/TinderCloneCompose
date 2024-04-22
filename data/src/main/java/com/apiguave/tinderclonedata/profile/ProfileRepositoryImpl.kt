package com.apiguave.tinderclonedata.profile

import com.apiguave.tinderclonedomain.profile.Profile
import com.apiguave.tinderclonedomain.profile.ProfileRepository
import com.apiguave.tinderclonedomain.profile.UserProfile
import com.apiguave.tinderclonedomain.profile.Gender
import com.apiguave.tinderclonedomain.profile.LocalPicture
import com.apiguave.tinderclonedomain.profile.Orientation
import com.apiguave.tinderclonedomain.profile.Picture
import java.time.LocalDate

class ProfileRepositoryImpl(
    private val profileLocalDataSource: ProfileLocalDataSource,
    private val profileRemoteDataSource: ProfileRemoteDataSource
): ProfileRepository {

    override suspend fun getProfile(): UserProfile {
        return profileLocalDataSource.currentUser ?: kotlin.run {
            val currentUser = profileRemoteDataSource.getUserProfile()
            profileLocalDataSource.currentUser = currentUser
            currentUser
        }
    }

    override suspend fun updateProfile(
        bio: String,
        gender: Gender,
        orientation: Orientation,
        pictures: List<Picture>
    ): UserProfile {
        val currentUser = profileRemoteDataSource.updateProfile(getProfile(), bio, gender, orientation, pictures)
        profileLocalDataSource.currentUser = currentUser
        return currentUser
    }

    override suspend fun createProfile(
        userId: String,
        name: String,
        birthdate: LocalDate,
        bio: String,
        gender: Gender,
        orientation: Orientation,
        pictures: List<LocalPicture>
    ) {
        profileRemoteDataSource.createProfile(userId, name, birthdate, bio, gender, orientation, pictures)
    }

    override suspend fun likeProfile(profile: Profile): String? {
        return profileRemoteDataSource.likeProfile(profile)
    }

    override suspend fun passProfile(profile: Profile) {
        profileRemoteDataSource.passProfile(profile)
    }

    override suspend fun getProfiles(): List<Profile> {
        val currentUser = getProfile()
        return profileRemoteDataSource.getProfiles(currentUser)
    }

}