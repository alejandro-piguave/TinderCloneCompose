package com.apiguave.message_domain.repository

import com.apiguave.message_domain.model.Message
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    fun getMessages(matchId: String): Flow<List<Message>>
    suspend fun addMessage(matchId: String, text: String)
}