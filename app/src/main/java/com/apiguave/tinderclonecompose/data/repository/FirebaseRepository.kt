package com.apiguave.tinderclonecompose.data.repository

import com.apiguave.tinderclonecompose.data.Profile
import com.apiguave.tinderclonecompose.extensions.toAge
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

object FirebaseRepository {
    private val storageRepository = StorageRepository()
    private val firestoreRepository = FirestoreRepository()
    suspend fun createUserProfile(profile: CreateUserProfile) {
        createUserProfile(AuthRepository.userId!!, profile)
    }

    suspend fun createUserProfile(userId: String, profile: CreateUserProfile) {
        val filenames = storageRepository.uploadUserPictures(userId, profile.pictures)
        firestoreRepository.createUserProfile(userId, profile.name, profile.birthdate, profile.bio, profile.isMale, profile.orientation, filenames)
    }

    suspend fun getProfiles(): List<Profile>{
        val firestoreUserModels = firestoreRepository.getCompatibleUsers()
        //Fetch uris
        val profileUris = coroutineScope {
            firestoreUserModels.map { async{ storageRepository.getUrisFromUser(it.id!!, it.pictures )} }.awaitAll()
        }

        //Merge both and return a list of profiles
        return firestoreUserModels.zip(profileUris) { userModel, uris ->
            Profile(userModel.id!!, userModel.name ?: "", userModel.birthDate!!.toAge(), uris)
        }
    }

}