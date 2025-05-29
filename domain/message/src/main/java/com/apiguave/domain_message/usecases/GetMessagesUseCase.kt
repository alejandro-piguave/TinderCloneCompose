package com.apiguave.domain_message.usecases

import com.apiguave.domain_message.model.Message
import com.apiguave.domain_message.repository.MessageRepository
import kotlinx.coroutines.flow.Flow

class GetMessagesUseCase(private val messageRepository: MessageRepository) {

    operator fun invoke(matchId: String): Flow<List<Message>> = messageRepository.getMessages(matchId)
}