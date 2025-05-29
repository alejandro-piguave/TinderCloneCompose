package com.apiguave.data_message.source

import com.apiguave.tinderclonedomain.message.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.apiguave.core_network.MessageApi
import com.apiguave.core_network.AuthApi
import com.apiguave.data_message.repository.MessageRemoteDataSource

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