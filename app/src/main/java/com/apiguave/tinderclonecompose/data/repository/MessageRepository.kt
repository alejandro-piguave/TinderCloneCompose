package com.apiguave.tinderclonecompose.data.repository

import com.apiguave.tinderclonecompose.data.datasource.FirestoreDataSource

object MessageRepository {
    private val firestoreDataSource = FirestoreDataSource()

    fun getMessages(matchId: String) = firestoreDataSource.getMessages(matchId)

    suspend fun sendMessage(matchId: String, text: String) {
        firestoreDataSource.sendMessage(matchId, text)
    }
}