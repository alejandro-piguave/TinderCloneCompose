package com.apiguave.tinderclonecompose.data.message.datasource

import com.apiguave.tinderclonecompose.data.api.auth.AuthApi
import com.apiguave.tinderclonecompose.data.api.message.MessageApi
import com.apiguave.tinderclonecompose.data.message.repository.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MessageRemoteDataSource(private val messageApi: MessageApi, private val authApi: AuthApi) {

    fun getMessages(matchId: String): Flow<List<Message>> = messageApi.getMessages(matchId).map { list ->
        list.map {
            val isSender = it.senderId == authApi.userId
            val text = it.message
            Message(text, isSender)
        }
    }

    suspend fun sendMessage(matchId: String, text: String){
        messageApi.sendMessage(authApi.userId, matchId, text)
    }
}