package com.apiguave.tinderclonecompose.data.repository

import com.apiguave.tinderclonecompose.data.datasource.FirestoreRemoteDataSource

object MessageRepository {
    private val firestoreDataSource = FirestoreRemoteDataSource()

    fun getMessages(matchId: String) = firestoreDataSource.getMessages(matchId)

    suspend fun sendMessage(matchId: String, text: String) {
        firestoreDataSource.sendMessage(matchId, text)
    }
}