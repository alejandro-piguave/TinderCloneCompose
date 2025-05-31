package com.apiguave.domain_message.usecases

import com.apiguave.domain_message.repository.MessageRepository

class SendMessageUseCase(private val messageRepository: MessageRepository) {

    suspend operator fun invoke(matchId: String, message: String): Result<Unit> {
        return Result.runCatching { messageRepository.addMessage(matchId, message) }
    }
}