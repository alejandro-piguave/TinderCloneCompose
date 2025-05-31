package com.apiguave.message_data.repository

import com.apiguave.message_data.source.MessageFirebaseDataSource
import com.apiguave.message_domain.repository.MessageRepository


class MessageRepositoryImpl(
    private val messageRemoteDataSource: MessageFirebaseDataSource
):
    MessageRepository {

    override fun getMessages(matchId: String) = messageRemoteDataSource.getMessages(matchId)

    override suspend fun addMessage(matchId: String, text: String) {
        messageRemoteDataSource.sendMessage( matchId, text)
    }
}