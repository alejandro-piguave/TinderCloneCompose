package com.apiguave.data_message.source

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.apiguave.core_firebase.MessageApi
import com.apiguave.core_firebase.AuthApi
import com.apiguave.data_message.repository.MessageRemoteDataSource
import com.apiguave.domain_message.model.Message

class MessageRemoteDataSourceImpl: MessageRemoteDataSource {

    override fun getMessages(matchId: String): Flow<List<Message>> = MessageApi.getMessages(matchId).map { list ->
        list.map {
            val isSender = it.senderId == AuthApi.userId!!
            val text = it.message
            Message(text, isSender)
        }
    }

    override suspend fun sendMessage(matchId: String, text: String){
        MessageApi.sendMessage(matchId, text)
    }
}