package com.apiguave.tinderclonecompose.data.impl

import com.apiguave.tinderclonecompose.data.message.datasource.MessageRemoteDataSource
import com.apiguave.tinderclonecompose.data.message.repository.MessageRepository

class MessageRepositoryImpl(private val messageRemoteDataSource: MessageRemoteDataSource): MessageRepository {

    override fun getMessages(matchId: String) = messageRemoteDataSource.getMessages(matchId)

    override suspend fun sendMessage(matchId: String, text: String) {
        messageRemoteDataSource.sendMessage(matchId, text)
    }
}