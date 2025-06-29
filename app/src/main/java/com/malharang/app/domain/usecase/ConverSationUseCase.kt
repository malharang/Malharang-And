package com.malharang.app.domain.usecase

import com.malharang.app.data.mapper.todomain.toDomain
import com.malharang.app.data.mapper.toentity.toEntity
import com.malharang.app.domain.model.ConversationData
import com.malharang.app.domain.repository.ConversationRepository
import javax.inject.Inject

class InsertConversationUseCase @Inject constructor(
    private val repository: ConversationRepository
) {
    suspend operator fun invoke(conversation: ConversationData): Long {
        return repository.insertConversation(conversation.toEntity())
    }
}

class GetAllConversationsUseCase @Inject constructor(
    private val repository: ConversationRepository
) {
    suspend operator fun invoke(): List<ConversationData> {
        return repository.getAllConversations().map { it.toDomain() }
    }
}

class GetConversationByIdUseCase @Inject constructor(
    private val repository: ConversationRepository
) {
    suspend operator fun invoke(id: Long): ConversationData? {
        return repository.getConversationById(id)?.toDomain()
    }
}

class DeleteConversationUseCase @Inject constructor(
    private val repository: ConversationRepository
) {
    suspend operator fun invoke(conversation: ConversationData) {
        repository.deleteConversation(conversation.toEntity())
    }
}
