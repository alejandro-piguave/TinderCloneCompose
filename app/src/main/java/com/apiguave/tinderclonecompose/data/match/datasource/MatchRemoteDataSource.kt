package com.apiguave.tinderclonecompose.data.match.datasource

import com.apiguave.tinderclonecompose.data.account.exception.AuthException
import com.apiguave.tinderclonecompose.data.extension.getTaskResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MatchRemoteDataSource {
    companion object{
        private const val MATCHES = "matches"
    }
    private val currentUserId: String
        get() = FirebaseAuth.getInstance().currentUser?.uid ?: throw AuthException("User not logged in")

    suspend fun getFirestoreMatchModels(): List<FirestoreMatch> {
        val query = FirebaseFirestore.getInstance().collection(MATCHES)
            .whereArrayContains(FirestoreMatchProperties.usersMatched, currentUserId)
        val result = query.get().getTaskResult()
        return result.toObjects(FirestoreMatch::class.java)
    }
}