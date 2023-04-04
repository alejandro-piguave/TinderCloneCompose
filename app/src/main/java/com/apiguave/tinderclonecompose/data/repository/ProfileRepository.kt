package com.apiguave.tinderclonecompose.data.repository

import com.apiguave.tinderclonecompose.data.datasource.AuthDataSource
import com.apiguave.tinderclonecompose.data.datasource.FirestoreRemoteDataSource
import com.apiguave.tinderclonecompose.data.datasource.StorageRemoteDataSource
import com.apiguave.tinderclonecompose.data.datasource.model.FirestoreUserProperties
import com.apiguave.tinderclonecompose.data.repository.model.CreateUserProfile
import com.apiguave.tinderclonecompose.data.repository.model.CurrentProfile
import com.apiguave.tinderclonecompose.data.repository.model.FirebasePicture
import com.apiguave.tinderclonecompose.data.repository.model.UserPicture

object ProfileRepository {
    private val authDataSource = AuthDataSource()
    private val storageDataSource = StorageRemoteDataSource()
    private val firestoreDataSource = FirestoreRemoteDataSource()

    suspend fun updateProfile(currentProfile: CurrentProfile,
                              newBio: String, newGenderIndex: Int,
                              newOrientationIndex: Int,
                              newPictures: List<UserPicture>): CurrentProfile {

        val arePicturesEqual = currentProfile.pictures == newPictures
        val isDataEqual = currentProfile.isDataEqual(newBio, newGenderIndex, newOrientationIndex)

        if(arePicturesEqual && isDataEqual){
            return currentProfile
        } else if(arePicturesEqual){
            val data = currentProfile.toModifiedData(newBio, newGenderIndex, newOrientationIndex)
            firestoreDataSource.updateProfileData(data)
            return currentProfile.toModifiedProfile(
                newBio,
                newGenderIndex,
                newOrientationIndex
            )
        } else if(isDataEqual){
            val firebasePictures = updateProfilePictures(currentProfile.pictures, newPictures)
            return currentProfile.copy(pictures = firebasePictures)
        } else {
            val data = currentProfile.toModifiedData(newBio, newGenderIndex, newOrientationIndex)
            val firebasePictures = updateProfileDataAndPictures(data, currentProfile.pictures, newPictures)
            return currentProfile.toModifiedProfile(
                newBio,
                newGenderIndex,
                newOrientationIndex,
                firebasePictures
            )
        }
    }

    private suspend fun updateProfilePictures(outdatedPictures: List<FirebasePicture>,
                                              updatedPictures: List<UserPicture>): List<FirebasePicture>{
        val filenames = storageDataSource.updateProfilePictures(authDataSource.userId, outdatedPictures, updatedPictures)
        val updatedData = mapOf<String, Any>(FirestoreUserProperties.pictures to filenames.map { it.filename })
        firestoreDataSource.updateProfileData(updatedData)
        return filenames
    }

    private suspend fun updateProfileDataAndPictures(data: Map<String, Any>,
                                                     outdatedPictures: List<FirebasePicture>,
                                                     updatedPictures: List<UserPicture>): List<FirebasePicture>{
        val filenames = storageDataSource.updateProfilePictures(authDataSource.userId, outdatedPictures, updatedPictures)
        val updatedData = data + mapOf<String, Any>(FirestoreUserProperties.pictures to filenames.map { it.filename })
        firestoreDataSource.updateProfileData(updatedData)
        return filenames
    }

    suspend fun createUserProfile(profile: CreateUserProfile) {
        createUserProfile(authDataSource.userId, profile)
    }

    suspend fun createUserProfile(userId: String, profile: CreateUserProfile) {
        val filenames = storageDataSource.uploadUserPictures(userId, profile.pictures)
        firestoreDataSource.createUserProfile(
            userId,
            profile.name,
            profile.birthdate,
            profile.bio,
            profile.isMale,
            profile.orientation,
            filenames.map { it.filename }
        )
    }
}