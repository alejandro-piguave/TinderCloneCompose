package com.apiguave.message_data.repository

import com.apiguave.message_domain.model.Message
import kotlinx.coroutines.flow.Flow

interface MessageRemoteDataSource {
    fun getMessages(matchId: String): Flow<List<Message>>
    suspend fun sendMessage(matchId: String, text: String)
}