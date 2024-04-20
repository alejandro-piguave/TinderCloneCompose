package com.apiguave.tinderclonedata.message

import com.apiguave.tinderclonedomain.message.MessageRepository

class MessageRepositoryImpl(private val messageRemoteDataSource: MessageRemoteDataSource):
    MessageRepository {

    override fun getMessages(matchId: String) = messageRemoteDataSource.getMessages(matchId)

    override suspend fun sendMessage(matchId: String, text: String) {
        messageRemoteDataSource.sendMessage(matchId, text)
    }
}