package com.malharang.app.data.repositoryimpl

import com.malharang.app.data.local.dao.MessageDao
import com.malharang.app.data.local.entity.MessageEntity
import com.malharang.app.domain.repository.MessageRepository
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val messageDao: MessageDao
) : MessageRepository {

    override suspend fun insertMessage(message: MessageEntity): Long {
        return messageDao.insertMessage(message)
    }

    override suspend fun getMessagesByConversationId(conversationId: Long): List<MessageEntity> {
        return messageDao.getMessagesByConversationId(conversationId)
    }

    override suspend fun deleteMessagesByConversationId(conversationId: Long) {
        messageDao.deleteMessagesByConversationId(conversationId)
    }

    override suspend fun updateMessage(message: MessageEntity): Long {
        return messageDao.insertMessage(message)
    }
}
