package com.malharang.app.domain.repository

import com.malharang.app.data.local.entity.ConversationEntity

interface ConversationRepository {
    suspend fun insertConversation(entity: ConversationEntity): Long
    suspend fun getConversationById(id: Long): ConversationEntity?
    suspend fun getAllConversations(): List<ConversationEntity>
    suspend fun deleteConversation(entity: ConversationEntity)
    suspend fun updateConversationMode(id: Long, mode: String)
}
