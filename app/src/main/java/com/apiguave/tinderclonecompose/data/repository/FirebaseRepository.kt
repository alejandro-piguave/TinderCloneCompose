package com.apiguave.tinderclonecompose.data.repository

import com.apiguave.tinderclonecompose.data.*
import com.apiguave.tinderclonecompose.extensions.toAge
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

object FirebaseRepository {
    private val storageRepository = StorageRepository()
    private val firestoreRepository = FirestoreRepository()
    suspend fun createUserProfile(profile: CreateUserProfile) {
        createUserProfile(AuthRepository.userId, profile)
    }

    suspend fun createUserProfile(userId: String, profile: CreateUserProfile) {
        val filenames = storageRepository.uploadUserPictures(userId, profile.pictures)
        firestoreRepository.createUserProfile(userId, profile.name, profile.birthdate, profile.bio, profile.isMale, profile.orientation, filenames)
    }

    suspend fun getProfiles(): List<Profile>{
        val firestoreUserModels = firestoreRepository.getCompatibleUsers()
        //Fetch profiles
        val profiles = coroutineScope {
            firestoreUserModels.map { async{ getProfile(it) } }.awaitAll()
        }
        return profiles.filterNotNull()
    }

    private suspend fun getProfile(userModel: FirestoreUserModel): Profile?{
        val userId = userModel.id ?: return null
        if(userModel.pictures.isEmpty()) return null
        val uris = storageRepository.getUrisFromUser(userId, userModel.pictures)
        return Profile(userId, userModel.name ?: "", userModel.birthDate?.toAge() ?: 99, uris)
    }

    suspend fun getMatches(): List<Match>{
        val matchModels = firestoreRepository.getFirestoreMatchModels()
        val matches = coroutineScope {
            matchModels.map { async { getMatch(it) } }.awaitAll()
        }
        return matches.filterNotNull()
    }

    private suspend fun getMatch(matchModel: FirestoreMatchModel): Match?{
        val matchId = matchModel.id ?: return null
        val userId = matchModel.usersMatched.firstOrNull { it != AuthRepository.userId } ?: return null
        val user = firestoreRepository.getFirestoreUserModel(userId)
        val uri = storageRepository.getUriFromUser(userId, user.pictures.first())
        return Match(matchId, user.birthDate?.toAge() ?: 99, userId, user.name?: "", uri,  null)
    }
}