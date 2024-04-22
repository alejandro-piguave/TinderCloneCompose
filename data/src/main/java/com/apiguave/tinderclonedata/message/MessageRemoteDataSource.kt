package com.apiguave.tinderclonedata.message

import com.apiguave.tinderclonedata.api.message.MessageApi
import com.apiguave.tinderclonedomain.message.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MessageRemoteDataSource(private val messageApi: MessageApi) {

    fun getMessages(userId: String, matchId: String): Flow<List<Message>> = messageApi.getMessages(matchId).map { list ->
        list.map {
            val isSender = it.senderId == userId
            val text = it.message
            Message(text, isSender)
        }
    }

    suspend fun sendMessage(userId: String, matchId: String, text: String){
        messageApi.sendMessage(userId, matchId, text)
    }
}