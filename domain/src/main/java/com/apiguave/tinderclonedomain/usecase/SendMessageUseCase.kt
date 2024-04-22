package com.apiguave.tinderclonedomain.usecase

import com.apiguave.tinderclonedomain.message.MessageRepository

class SendMessageUseCase(private val messageRepository: MessageRepository) {

    suspend operator fun invoke(matchId: String, message: String): Result<Unit> {
        return Result.runCatching { messageRepository.addMessage(matchId, message) }
    }
}