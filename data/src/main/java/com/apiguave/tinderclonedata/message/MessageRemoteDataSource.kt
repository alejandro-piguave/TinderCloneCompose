package com.apiguave.tinderclonedata.message

import com.apiguave.tinderclonedata.api.auth.AuthProvider
import com.apiguave.tinderclonedata.api.message.MessageApi
import com.apiguave.tinderclonedomain.message.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MessageRemoteDataSource(
    private val authProvider: AuthProvider,
    private val messageApi: MessageApi) {

    fun getMessages(matchId: String): Flow<List<Message>> = messageApi.getMessages(matchId).map { list ->
        list.map {
            val isSender = it.senderId == authProvider.userId!!
            val text = it.message
            Message(text, isSender)
        }
    }

    suspend fun sendMessage(matchId: String, text: String){
        messageApi.sendMessage(matchId, text)
    }
}