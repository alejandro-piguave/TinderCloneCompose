package com.apiguave.tinderclonedata.message.repository

import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    fun getMessages(matchId: String): Flow<List<Message>>
    suspend fun sendMessage(matchId: String, text: String)
}