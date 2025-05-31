package com.apiguave.message_domain.usecases

import com.apiguave.message_domain.model.Message
import com.apiguave.message_domain.repository.MessageRepository
import kotlinx.coroutines.flow.Flow

class GetMessagesUseCase(private val messageRepository: MessageRepository) {

    operator fun invoke(matchId: String): Flow<List<Message>> = messageRepository.getMessages(matchId)
}