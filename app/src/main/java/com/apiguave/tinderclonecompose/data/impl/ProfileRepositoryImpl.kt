package com.apiguave.tinderclonecompose.data.impl

import com.apiguave.tinderclonecompose.data.auth.AuthRepository
import com.apiguave.tinderclonecompose.data.profile.repository.ProfileRepository
import com.apiguave.tinderclonecompose.data.profile.repository.CreateUserProfile
import com.apiguave.tinderclonecompose.data.profile.repository.UserProfile
import com.apiguave.tinderclonecompose.data.profile.repository.Gender
import com.apiguave.tinderclonecompose.data.profile.repository.Orientation
import com.apiguave.tinderclonecompose.data.picture.Picture
import com.apiguave.tinderclonecompose.data.profile.datasource.ProfileLocalDataSource
import com.apiguave.tinderclonecompose.data.profile.datasource.ProfileRemoteDataSource

class ProfileRepositoryImpl(
    private val authRepository: AuthRepository,
    private val profileLocalDataSource: ProfileLocalDataSource,
    private val profileRemoteDataSource: ProfileRemoteDataSource
): ProfileRepository {

    override suspend fun getProfile(): UserProfile {
        return profileLocalDataSource.currentUser ?: kotlin.run {
            val currentUser = profileRemoteDataSource.getUserProfile(authRepository.userId)
            profileLocalDataSource.currentUser = currentUser
            currentUser
        }
    }

    override suspend fun createProfile(profile: CreateUserProfile) {
        profileRemoteDataSource.createProfile(authRepository.userId, profile)
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
}