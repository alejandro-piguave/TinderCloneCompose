package com.apiguave.tinderclonecompose.data.repository

import com.apiguave.tinderclonecompose.data.*
import com.apiguave.tinderclonecompose.extensions.*
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

object FirebaseRepository {
    private val storageRepository = StorageRepository()
    private val firestoreRepository = FirestoreRepository()

    fun getMessages(matchId: String) = firestoreRepository.getMessages(matchId)

    suspend fun sendMessage(matchId: String, text: String) {
        firestoreRepository.sendMessage(matchId, text)
    }

    suspend fun swipeUser(profile: Profile, isLike: Boolean): NewMatch? {
        val matchModel = firestoreRepository.swipeUser(profile.id, isLike)
        return matchModel?.let { model ->
            NewMatch(model.id, profile.id, profile.name, profile.pictures)
        }
    }

    suspend fun createUserProfile(profile: CreateUserProfile) {
        createUserProfile(AuthRepository.userId, profile)
    }

    suspend fun createUserProfile(userId: String, profile: CreateUserProfile) {
        val filenames = storageRepository.uploadUserPictures(userId, profile.pictures)
        firestoreRepository.createUserProfile(
            userId,
            profile.name,
            profile.birthdate,
            profile.bio,
            profile.isMale,
            profile.orientation,
            filenames
        )
    }

    suspend fun getProfiles(): ProfileList {
        val userList = firestoreRepository.getUserList()
        //Fetch profiles
        val profileList = coroutineScope {
            val profiles = userList.compatibleUsers.map { async { getProfile(it) } }
            val currentProfile = async { getCurrentProfile(userList.currentUser) }
            ProfileList(currentProfile.await(), profiles.awaitAll())
        }
        return profileList
    }

    private suspend fun getCurrentProfile(userModel: FirestoreUser): CurrentProfile {
        val uris = if (userModel.pictures.isEmpty()) emptyList() else storageRepository.getUrisFromUser(userModel.id, userModel.pictures)
        return userModel.toCurrentProfile(uris)
    }

    private suspend fun getProfile(userModel: FirestoreUser): Profile {
        val uris = if (userModel.pictures.isEmpty()) emptyList() else storageRepository.getUrisFromUser(userModel.id, userModel.pictures)
        return userModel.toProfile(uris)
    }

    suspend fun getMatches(): List<Match> {
        val matchModels = firestoreRepository.getFirestoreMatchModels()
        val matches = coroutineScope {
            matchModels.map { async { it.toMatch() } }.awaitAll()
        }
        return matches.filterNotNull()
    }

    private suspend fun FirestoreMatch.toMatch(): Match? {
        val userId = this.usersMatched.firstOrNull { it != AuthRepository.userId } ?: return null
        val user = firestoreRepository.getFirestoreUserModel(userId)
        val uri = storageRepository.getUriFromUser(userId, user.pictures.first())
        return Match(
            this.id,
            user.birthDate?.toAge() ?: 99,
            userId,
            user.name,
            uri,
            this.timestamp?.toShortString() ?: "",
            this.lastMessage
        )
    }
}