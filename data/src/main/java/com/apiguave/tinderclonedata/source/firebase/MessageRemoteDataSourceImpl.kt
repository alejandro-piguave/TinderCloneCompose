package com.apiguave.tinderclonedata.source.firebase

import com.apiguave.tinderclonedata.repository.message.MessageRemoteDataSource
import com.apiguave.tinderclonedomain.message.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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