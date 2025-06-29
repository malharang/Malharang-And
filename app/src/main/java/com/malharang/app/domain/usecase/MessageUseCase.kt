package com.malharang.app.domain.usecase

import com.malharang.app.data.mapper.todomain.toDomain
import com.malharang.app.data.mapper.toentity.toEntity
import com.malharang.app.domain.model.MessageData
import com.malharang.app.domain.repository.MessageRepository
import javax.inject.Inject

class InsertMessageUseCase @Inject constructor(
    private val repository: MessageRepository
) {
    suspend operator fun invoke(message: MessageData): Long {
        return repository.insertMessage(message.toEntity())
    }
}

class GetMessagesByConversationIdUseCase @Inject constructor(
    private val repository: MessageRepository
) {
    suspend operator fun invoke(conversationId: Long): List<MessageData> {
        return repository.getMessagesByConversationId(conversationId).map { it.toDomain() }
    }
}

class DeleteMessageUseCase @Inject constructor(
    private val repository: MessageRepository
) {
    suspend operator fun invoke(conversationId: Long) {
        repository.deleteMessagesByConversationId(conversationId)
    }
}

