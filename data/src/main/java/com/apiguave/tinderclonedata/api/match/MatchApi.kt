package com.apiguave.tinderclonedata.api.match

import com.apiguave.tinderclonedata.extension.getTaskResult
import com.apiguave.tinderclonedata.local.AuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class MatchApi(private val authProvider: AuthProvider) {
    companion object {
        private const val MATCHES = "matches"
    }

    suspend fun getMatches(): List<FirestoreMatch> {
        val query = FirebaseFirestore.getInstance().collection(MATCHES)
            .whereArrayContains(FirestoreMatchProperties.usersMatched, authProvider.userId!!)
        val result = query.get().getTaskResult()
        return result.toObjects(FirestoreMatch::class.java)
    }

    suspend fun getMatch(id: String): FirestoreMatch {
        return FirebaseFirestore.getInstance()
            .collection(MATCHES)
            .document(id)
            .get()
            .getTaskResult()
            .toObject()!!
    }
}