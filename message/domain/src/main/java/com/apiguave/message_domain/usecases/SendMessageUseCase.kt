package com.apiguave.message_domain.usecases

import com.apiguave.message_domain.repository.MessageRepository

class SendMessageUseCase(private val messageRepository: MessageRepository) {

    suspend operator fun invoke(matchId: String, message: String): Result<Unit> {
        return Result.runCatching { messageRepository.addMessage(matchId, message) }
    }
}