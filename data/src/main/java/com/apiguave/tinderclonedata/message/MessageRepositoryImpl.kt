package com.apiguave.tinderclonedata.message

import com.apiguave.tinderclonedomain.auth.AuthRepository
import com.apiguave.tinderclonedomain.message.MessageRepository

class MessageRepositoryImpl(
    private val authRepository: AuthRepository,
    private val messageRemoteDataSource: MessageRemoteDataSource):
    MessageRepository {

    override fun getMessages(matchId: String) = messageRemoteDataSource.getMessages(authRepository.userId!!, matchId)

    override suspend fun sendMessage(matchId: String, text: String) {
        messageRemoteDataSource.sendMessage(authRepository.userId!!, matchId, text)
    }
}