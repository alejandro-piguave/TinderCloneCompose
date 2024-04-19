package com.apiguave.tinderclonedata.impl

import com.apiguave.tinderclonedata.api.auth.AuthApi
import com.apiguave.tinderclonedata.profile.repository.ProfileRepository
import com.apiguave.tinderclonedata.profile.repository.CreateUserProfile
import com.apiguave.tinderclonedata.profile.repository.UserProfile
import com.apiguave.tinderclonedata.profile.repository.Gender
import com.apiguave.tinderclonedata.profile.repository.Orientation
import com.apiguave.tinderclonedata.picture.Picture
import com.apiguave.tinderclonedata.profile.datasource.ProfileLocalDataSource
import com.apiguave.tinderclonedata.profile.datasource.ProfileRemoteDataSource

class ProfileRepositoryImpl(
    private val authApi: AuthApi,
    private val profileLocalDataSource: ProfileLocalDataSource,
    private val profileRemoteDataSource: ProfileRemoteDataSource
): ProfileRepository {

    override suspend fun getProfile(): UserProfile {
        return profileLocalDataSource.currentUser ?: kotlin.run {
            val currentUser = profileRemoteDataSource.getUserProfile(authApi.userId)
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

    override suspend fun createProfile(createUserProfile: CreateUserProfile) {
        profileRemoteDataSource.createProfile(authApi.userId, createUserProfile)
    }

}