package com.apiguave.message_data.source

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.apiguave.core_firebase.MessageApi
import com.apiguave.core_firebase.AuthApi
import com.apiguave.message_domain.model.Message

class MessageFirebaseDataSource {

    fun getMessages(matchId: String): Flow<List<Message>> = MessageApi.getMessages(matchId).map { list ->
        list.map {
            val isSender = it.senderId == AuthApi.userId!!
            val text = it.message
            Message(text, isSender)
        }
    }

    suspend fun sendMessage(matchId: String, text: String){
        MessageApi.sendMessage(matchId, text)
    }
}