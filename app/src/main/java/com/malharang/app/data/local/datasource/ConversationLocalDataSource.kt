package com.malharang.app.data.local.datasource

import com.malharang.app.data.local.entity.ConversationEntity

interface ConversationLocalDataSource {
    suspend fun insertConversation(conversation: ConversationEntity): Long
    suspend fun getConversationById(id: Long): ConversationEntity?
    suspend fun getAllConversations(): List<ConversationEntity>
    suspend fun deleteConversation(conversation: ConversationEntity)
    suspend fun updateConversationMode(id: Long, mode: String)
}
