package com.apiguave.tinderclonecompose.data.api.match

import com.apiguave.tinderclonecompose.data.extension.getTaskResult
import com.google.firebase.firestore.FirebaseFirestore

class MatchApi {
    companion object{
        private const val MATCHES = "matches"
    }

    suspend fun getMatches(userId: String): List<FirestoreMatch> {
        val query = FirebaseFirestore.getInstance().collection(MATCHES)
            .whereArrayContains(FirestoreMatchProperties.usersMatched, userId)
        val result = query.get().getTaskResult()
        return result.toObjects(FirestoreMatch::class.java)
    }
}