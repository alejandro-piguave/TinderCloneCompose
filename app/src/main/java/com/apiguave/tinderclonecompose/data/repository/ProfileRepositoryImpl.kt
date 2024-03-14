package com.apiguave.tinderclonecompose.data.repository

import com.apiguave.tinderclonecompose.data.datasource.AuthRemoteDataSource
import com.apiguave.tinderclonecompose.data.datasource.FirestoreRemoteDataSource
import com.apiguave.tinderclonecompose.data.datasource.ProfileLocalDataSource
import com.apiguave.tinderclonecompose.data.datasource.StorageRemoteDataSource
import com.apiguave.tinderclonecompose.data.datasource.model.FirestoreUser
import com.apiguave.tinderclonecompose.data.datasource.model.FirestoreUserProperties
import com.apiguave.tinderclonecompose.data.extension.toBoolean
import com.apiguave.tinderclonecompose.data.extension.toFirestoreOrientation
import com.apiguave.tinderclonecompose.domain.profile.ProfileRepository
import com.apiguave.tinderclonecompose.domain.profile.entity.CreateUserProfile
import com.apiguave.tinderclonecompose.domain.home.entity.UserProfile
import com.apiguave.tinderclonecompose.domain.profile.entity.FirebasePicture
import com.apiguave.tinderclonecompose.domain.profile.entity.Gender
import com.apiguave.tinderclonecompose.domain.profile.entity.Orientation
import com.apiguave.tinderclonecompose.domain.profile.entity.UserPicture
import com.apiguave.tinderclonecompose.extensions.toUserProfile

class ProfileRepositoryImpl(
    private val authDataSource: AuthRemoteDataSource,
    private val storageDataSource: StorageRemoteDataSource,
    private val firestoreDataSource: FirestoreRemoteDataSource,
    private val profileLocalDataSource: ProfileLocalDataSource
): ProfileRepository {

    override fun getUserProfile(): UserProfile {
        return profileLocalDataSource.userProfile.toUserProfile(profileLocalDataSource.userPictures)
    }

    override suspend fun createUserProfile(profile: CreateUserProfile) {
        createUserProfile(authDataSource.userId, profile)
    }

    override suspend fun createUserProfile(userId: String, profile: CreateUserProfile) {
        val filenames = storageDataSource.uploadUserPictures(userId, profile.pictures)
        firestoreDataSource.createUserProfile(
            userId,
            profile.name,
            profile.birthdate,
            profile.bio,
            profile.isMale,
            profile.orientation.toFirestoreOrientation(),
            filenames.map { it.filename }
        )
    }

    override suspend fun updateProfile(
        bio: String,
        gender: Gender,
        orientation: Orientation,
        pictures: List<UserPicture>
    ): UserProfile {
        val currentProfile = profileLocalDataSource.userProfile
        val currentPictures = profileLocalDataSource.userPictures

        val arePicturesEqual = currentPictures == pictures
        val modifiedProfileData = currentProfile.getModifiedData(bio, gender, orientation)

        when {
            arePicturesEqual && modifiedProfileData != null -> {
                firestoreDataSource.updateProfileData(modifiedProfileData)
                profileLocalDataSource.userProfile = profileLocalDataSource.userProfile.copy(bio = bio, orientation = orientation.toFirestoreOrientation(), male = gender.toBoolean())
            }
            !arePicturesEqual && modifiedProfileData == null -> {
                val firebasePictures = updateProfilePictures(currentPictures, pictures)
                profileLocalDataSource.userPictures = firebasePictures
            }
            !arePicturesEqual && modifiedProfileData != null -> {
                val firebasePictures = updateProfileDataAndPictures(modifiedProfileData, currentPictures, pictures)
                profileLocalDataSource.userProfile = profileLocalDataSource.userProfile.copy(bio = bio, orientation = orientation.toFirestoreOrientation(), male = gender.toBoolean())
                profileLocalDataSource.userPictures = firebasePictures
            }
        }

        return getUserProfile()
    }
    private fun FirestoreUser.getModifiedData(bio: String, gender: Gender, orientation: Orientation): Map<String, Any>? {
        val data = mutableMapOf<String, Any>()
        if(bio != this.bio){
            data[FirestoreUserProperties.bio] = bio
        }
        if(gender.toBoolean() != this.male){
            data[FirestoreUserProperties.isMale] = gender.toBoolean()
        }
        if(orientation.toFirestoreOrientation() != this.orientation){
            data[FirestoreUserProperties.orientation] = orientation.toFirestoreOrientation()
        }

        return if(data.isEmpty()) null else data
    }

    private suspend fun updateProfilePictures(
        outdatedPictures: List<FirebasePicture>,
        updatedPictures: List<UserPicture>
    ): List<FirebasePicture> {
        val filenames = storageDataSource.updateProfilePictures(
            authDataSource.userId,
            outdatedPictures,
            updatedPictures
        )
        val updatedData =
            mapOf<String, Any>(FirestoreUserProperties.pictures to filenames.map { it.filename })
        firestoreDataSource.updateProfileData(updatedData)
        return filenames
    }

    private suspend fun updateProfileDataAndPictures(
        data: Map<String, Any>,
        outdatedPictures: List<FirebasePicture>,
        updatedPictures: List<UserPicture>
    ): List<FirebasePicture> {
        val filenames = storageDataSource.updateProfilePictures(
            authDataSource.userId,
            outdatedPictures,
            updatedPictures
        )
        val updatedData =
            data + mapOf<String, Any>(FirestoreUserProperties.pictures to filenames.map { it.filename })
        firestoreDataSource.updateProfileData(updatedData)
        return filenames
    }

}