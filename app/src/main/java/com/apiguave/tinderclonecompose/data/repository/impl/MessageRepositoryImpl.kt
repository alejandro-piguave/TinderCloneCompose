package com.apiguave.tinderclonecompose.data.repository.impl

import com.apiguave.tinderclonecompose.data.datasource.FirestoreRemoteDataSource
import com.apiguave.tinderclonecompose.data.repository.MessageRepository

class MessageRepositoryImpl(private val firestoreDataSource : FirestoreRemoteDataSource):
    MessageRepository {

    override fun getMessages(matchId: String) = firestoreDataSource.getMessages(matchId)

    override suspend fun sendMessage(matchId: String, text: String) {
        firestoreDataSource.sendMessage(matchId, text)
    }
}