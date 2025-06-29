package com.malharang.app.domain.repository

import com.malharang.app.data.local.entity.MessageEntity

interface MessageRepository {
    suspend fun insertMessage(message: MessageEntity): Long
    suspend fun getMessagesByConversationId(conversationId: Long): List<MessageEntity>
    suspend fun deleteMessagesByConversationId(conversationId: Long)
}