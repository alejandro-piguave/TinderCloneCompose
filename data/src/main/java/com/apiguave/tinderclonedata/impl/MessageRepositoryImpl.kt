package com.apiguave.tinderclonedata.impl

import com.apiguave.tinderclonedata.message.datasource.MessageRemoteDataSource
import com.apiguave.tinderclonedata.message.repository.MessageRepository

class MessageRepositoryImpl(private val messageRemoteDataSource: MessageRemoteDataSource):
    MessageRepository {

    override fun getMessages(matchId: String) = messageRemoteDataSource.getMessages(matchId)

    override suspend fun sendMessage(matchId: String, text: String) {
        messageRemoteDataSource.sendMessage(matchId, text)
    }
}