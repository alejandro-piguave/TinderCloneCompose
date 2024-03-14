package com.apiguave.tinderclonecompose.data.datasource

import com.apiguave.tinderclonecompose.data.account.exception.AuthException
import com.apiguave.tinderclonecompose.data.datasource.exception.FirestoreException
import com.apiguave.tinderclonecompose.data.datasource.model.FirestoreMatch
import com.apiguave.tinderclonecompose.data.datasource.model.FirestoreMatchProperties
import com.apiguave.tinderclonecompose.data.user.datasource.FirestoreUser
import com.apiguave.tinderclonecompose.data.user.datasource.FirestoreUserProperties
import com.apiguave.tinderclonecompose.data.extension.getTaskResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class FirestoreRemoteDataSource {
    companion object {
        private const val USERS = "users"
        private const val MATCHES = "matches"
    }
    
    private val currentUserId: String
        get() = FirebaseAuth.getInstance().currentUser?.uid ?: throw AuthException("User not logged in")

    suspend fun swipeUser(swipedUserId: String, isLike: Boolean): FirestoreMatch? {
        FirebaseFirestore.getInstance()
            .collection(USERS)
            .document(currentUserId)
            .update(mapOf((if (isLike) FirestoreUserProperties.liked else FirestoreUserProperties.passed) to FieldValue.arrayUnion(swipedUserId)))
            .getTaskResult()
        FirebaseFirestore.getInstance()
            .collection(USERS)
            .document(currentUserId)
            .collection(FirestoreUserProperties.liked)
            .document(swipedUserId)
            .set(mapOf("exists" to true))
            .getTaskResult()

        val hasUserLikedBack = hasUserLikedBack(swipedUserId)
        if(hasUserLikedBack){
            val matchId = getMatchId(currentUserId, swipedUserId)
            FieldValue.serverTimestamp()
            val data = FirestoreMatchProperties.toData(swipedUserId, currentUserId)
            FirebaseFirestore.getInstance()
                .collection(MATCHES)
                .document(matchId)
                .set(data)
                .getTaskResult()

            val result = FirebaseFirestore.getInstance()
                .collection(MATCHES)
                .document(matchId)
                .get()
                .getTaskResult()
            return result.toObject()
        }
        return null
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
            .document(currentUserId)
            .get()
            .getTaskResult()
        return result.exists()
    }

    suspend fun getFirestoreMatchModels(): List<FirestoreMatch> {
        val query = FirebaseFirestore.getInstance().collection(MATCHES)
            .whereArrayContains(FirestoreMatchProperties.usersMatched, currentUserId)
        val result = query.get().getTaskResult()
        return result.toObjects(FirestoreMatch::class.java)
    }

    suspend fun getFirestoreUserModel(userId: String): FirestoreUser {
        val snapshot = FirebaseFirestore.getInstance().collection(USERS).document(userId).get().getTaskResult()
        return snapshot.toObject<FirestoreUser>() ?: throw FirestoreException("Document doesn't exist")
    }
}