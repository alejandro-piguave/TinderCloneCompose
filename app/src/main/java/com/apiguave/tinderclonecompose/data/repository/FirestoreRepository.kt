package com.apiguave.tinderclonecompose.data.repository

import com.apiguave.tinderclonecompose.data.*
import com.apiguave.tinderclonecompose.extensions.getTaskResult
import com.apiguave.tinderclonecompose.extensions.toTimestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import java.time.LocalDate

class FirestoreRepository {
    companion object {
        private const val USERS = "users"
        private const val MATCHES = "matches"
    }

    suspend fun swipeUser(swipedUserId: String, isLike: Boolean): Boolean {
        FirebaseFirestore.getInstance()
            .collection(USERS)
            .document(AuthRepository.userId)
            .update(mapOf((if (isLike) FirestoreUserProperties.liked else FirestoreUserProperties.passed) to FieldValue.arrayUnion(swipedUserId)))
            .getTaskResult()
        FirebaseFirestore.getInstance()
            .collection(USERS)
            .document(AuthRepository.userId)
            .collection(FirestoreUserProperties.liked)
            .document(swipedUserId)
            .set(mapOf("exists" to true))
            .getTaskResult()

        val hasUserLikedBack = hasUserLikedBack(swipedUserId)
        if(hasUserLikedBack){
            val matchId = getMatchId(AuthRepository.userId, swipedUserId)
            FieldValue.serverTimestamp()
            val data = mapOf(
                FirestoreMatchProperties.usersMatched to listOf(swipedUserId, AuthRepository.userId),
                FirestoreMatchProperties.timestamp to FieldValue.serverTimestamp()
            )
            FirebaseFirestore.getInstance()
                .collection(MATCHES)
                .document(matchId)
                .set(data)
                .getTaskResult()
        }
        return hasUserLikedBack
    }

    private fun getMatchId(userId1: String, userId2: String): String{
        return if(userId1 > userId2){
            userId1 + userId2
        } else userId2 + userId1
    }

    private suspend fun hasUserLikedBack(swipedUserId: String): Boolean{
        val result = FirebaseFirestore.getInstance()
            .collection(USERS)
            .document(swipedUserId)
            .collection(FirestoreUserProperties.liked)
            .document(AuthRepository.userId)
            .get()
            .getTaskResult()
        return result.exists()
    }

    suspend fun createUserProfile(
        userId: String,
        name: String,
        birthdate: LocalDate,
        bio: String,
        isMale: Boolean,
        orientation: Orientation,
        pictures: List<String>
    ) {
        val user = FirestoreUser(
            name = name,
            birthDate = birthdate.toTimestamp(),
            bio = bio,
            male = isMale,
            orientation = orientation.name,
            pictures = pictures,
            liked = emptyList(),
            passed = emptyList()
        )
        FirebaseFirestore.getInstance().collection(USERS).document(userId).set(user).getTaskResult()
    }

    suspend fun getCompatibleUsers(): List<FirestoreUser> {
        //Get current user information
        val currentUser = getFirestoreUserModel(AuthRepository.userId)
        currentUser.male ?: throw FirestoreException("Couldn't find field 'isMale' for the current user.")
        val excludedUserIds = currentUser.liked + currentUser.passed

        //Build query
        val searchQuery: Query = kotlin.run {
            val query = FirebaseFirestore.getInstance().collection(USERS)
                .whereNotEqualTo(
                    FirestoreUserProperties.orientation,
                    if (currentUser.male)
                        Orientation.women.name
                    else Orientation.men.name
                )
            if (currentUser.orientation != Orientation.both.name) {
                query.whereEqualTo(
                    FirestoreUserProperties.isMale,
                    currentUser.orientation == Orientation.men.name
                )
            } else query
        }

        val result = searchQuery.get().getTaskResult()
        //Filter documents
        val filteredDocumentList = result.filter { it.id != currentUser.id && !excludedUserIds.contains(it.id) }
        return filteredDocumentList.mapNotNull { it.toObject() }
    }

    suspend fun getFirestoreMatchModels(): List<FirestoreMatch> {
        val query = FirebaseFirestore.getInstance().collection(MATCHES)
            .whereArrayContains(FirestoreMatchProperties.usersMatched, AuthRepository.userId)
        val result = query.get().getTaskResult()
        return result.toObjects(FirestoreMatch::class.java)
    }

    suspend fun getFirestoreUserModel(userId: String): FirestoreUser {
        val snapshot = FirebaseFirestore.getInstance().collection(USERS).document(userId).get().getTaskResult()
        return snapshot.toObject<FirestoreUser>() ?: throw FirestoreException("Document doesn't exist")
    }
}