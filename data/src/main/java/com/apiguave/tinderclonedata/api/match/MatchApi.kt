package com.apiguave.tinderclonedata.api.match

import com.apiguave.tinderclonecompose.data.extension.getTaskResult
import com.apiguave.tinderclonedata.api.auth.AuthProvider
import com.google.firebase.firestore.FirebaseFirestore

class MatchApi(private val authProvider: AuthProvider) {
    companion object{
        private const val MATCHES = "matches"
    }

    suspend fun getMatches(): List<FirestoreMatch> {
        val query = FirebaseFirestore.getInstance().collection(MATCHES)
            .whereArrayContains(FirestoreMatchProperties.usersMatched, authProvider.userId!!)
        val result = query.get().getTaskResult()
        return result.toObjects(FirestoreMatch::class.java)
    }
}