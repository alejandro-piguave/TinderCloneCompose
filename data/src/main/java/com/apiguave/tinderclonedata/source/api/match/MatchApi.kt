package com.apiguave.tinderclonedata.source.api.match

import com.apiguave.tinderclonedata.source.extension.getTaskResult
import com.apiguave.tinderclonedata.source.api.auth.AuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

object MatchApi {
    private const val MATCHES = "matches"

    suspend fun getMatches(): List<FirestoreMatch> {
        val query = FirebaseFirestore.getInstance().collection(MATCHES)
            .whereArrayContains(FirestoreMatchProperties.usersMatched, AuthProvider.userId!!)
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