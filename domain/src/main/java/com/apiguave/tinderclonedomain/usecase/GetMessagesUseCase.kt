package com.apiguave.tinderclonedomain.usecase

import com.apiguave.tinderclonedomain.message.Message
import com.apiguave.tinderclonedomain.message.MessageRepository
import kotlinx.coroutines.flow.Flow

class GetMessagesUseCase(private val messageRepository: MessageRepository) {

    operator fun invoke(matchId: String): Flow<List<Message>> = messageRepository.getMessages(matchId)
}