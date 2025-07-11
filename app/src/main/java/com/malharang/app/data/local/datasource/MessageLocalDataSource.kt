package com.malharang.app.data.local.datasource

import com.malharang.app.data.local.entity.MessageEntity

interface MessageLocalDataSource {
    suspend fun insertMessage(message: MessageEntity): Long
    suspend fun getMessagesByConversationId(conversationId: Long): List<MessageEntity>
    suspend fun deleteMessagesByConversationId(conversationId: Long)
}
