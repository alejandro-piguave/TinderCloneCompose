package com.apiguave.tinderclonedata.repository.profile

import com.apiguave.tinderclonedata.source.profile.ProfileRemoteDataSourceImpl
import com.apiguave.tinderclonedomain.profile.Profile
import com.apiguave.tinderclonedomain.profile.ProfileRepository
import com.apiguave.tinderclonedomain.profile.UserProfile
import com.apiguave.tinderclonedomain.profile.Gender
import com.apiguave.tinderclonedomain.profile.LocalPicture
import com.apiguave.tinderclonedomain.profile.Orientation
import com.apiguave.tinderclonedomain.profile.Picture
import java.time.LocalDate

class ProfileRepositoryImpl(
    private val profileRemoteDataSource: ProfileRemoteDataSourceImpl
): ProfileRepository {

    override suspend fun getProfile(): UserProfile {
        return profileRemoteDataSource.getUserProfile()
    }

    override suspend fun updateProfile(
        bio: String,
        gender: Gender,
        orientation: Orientation,
        pictures: List<Picture>
    ): UserProfile {
        return profileRemoteDataSource.updateProfile(bio, gender, orientation, pictures)
    }

    override suspend fun addProfile(
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
        return profileRemoteDataSource.getProfiles()
    }

}