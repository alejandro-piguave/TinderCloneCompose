package com.apiguave.message_data.repository

import com.apiguave.message_domain.model.Message
import com.apiguave.message_domain.repository.MessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeMessageRepositoryImpl: MessageRepository {
    private var messageList = listOf(
        Message("Hey, what's up?", true),
        Message("Not much, what about you?", false),
        Message("Same, just chilling at my place", true)
    )
    private val messageFlow = MutableStateFlow(messageList)
    override fun getMessages(matchId: String): Flow<List<Message>> = messageFlow

    override suspend fun addMessage(matchId: String, text: String) {
        messageList = messageList + Message(text, true)
        messageFlow.emit(messageList)
    }
}