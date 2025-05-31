package com.apiguave.domain_message.repository

import com.apiguave.domain_message.model.Message
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    fun getMessages(matchId: String): Flow<List<Message>>
    suspend fun addMessage(matchId: String, text: String)
}