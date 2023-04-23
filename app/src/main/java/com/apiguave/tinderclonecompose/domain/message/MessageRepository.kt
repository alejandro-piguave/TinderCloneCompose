package com.apiguave.tinderclonecompose.domain.message

import com.apiguave.tinderclonecompose.domain.message.entity.Message
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    fun getMessages(matchId: String): Flow<List<Message>>
    suspend fun sendMessage(matchId: String, text: String)
}