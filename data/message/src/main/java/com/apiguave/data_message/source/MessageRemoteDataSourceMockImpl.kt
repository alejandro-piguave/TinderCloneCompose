package com.apiguave.data_message.source

import com.apiguave.data_message.repository.MessageRemoteDataSource
import com.apiguave.tinderclonedomain.message.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class MessageRemoteDataSourceMockImpl: MessageRemoteDataSource {
    private var messageList = listOf(
        Message("Hey, what's up?", true),
        Message("Not much, what about you?", false),
        Message("Same, just chilling at my place", true)
    )
    private val messageFlow = MutableStateFlow(messageList)
    override fun getMessages(matchId: String): Flow<List<Message>> = messageFlow

    override suspend fun sendMessage(matchId: String, text: String) {
        messageList = messageList + Message(text, true)
        messageFlow.emit(messageList)
    }
}