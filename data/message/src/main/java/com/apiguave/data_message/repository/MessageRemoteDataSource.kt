package com.apiguave.data_message.repository

import com.apiguave.domain_message.model.Message
import kotlinx.coroutines.flow.Flow

interface MessageRemoteDataSource {
    fun getMessages(matchId: String): Flow<List<Message>>
    suspend fun sendMessage(matchId: String, text: String)
}