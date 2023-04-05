package com.apiguave.tinderclonecompose.data.repository

import com.apiguave.tinderclonecompose.data.repository.model.Message
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    fun getMessages(matchId: String): Flow<List<Message>>
    suspend fun sendMessage(matchId: String, text: String)
}