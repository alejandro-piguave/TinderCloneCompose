package com.apiguave.tinderclonedomain.message

import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    fun getMessages(matchId: String): Flow<List<Message>>
    suspend fun addMessage(matchId: String, text: String)
}