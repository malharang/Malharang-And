package com.malharang.app.data.local.datasourceimpl

import com.malharang.app.data.local.dao.MessageDao
import com.malharang.app.data.local.datasource.MessageLocalDataSource
import com.malharang.app.data.local.entity.MessageEntity
import javax.inject.Inject

class MessageLocalDataSourceImpl @Inject constructor(
    private val messageDao: MessageDao
) : MessageLocalDataSource {

    override suspend fun insertMessage(message: MessageEntity): Long {
        return messageDao.insertMessage(message)
    }

    override suspend fun getMessagesByConversationId(conversationId: Long): List<MessageEntity> {
        return messageDao.getMessagesByConversationId(conversationId)
    }

    override suspend fun deleteMessagesByConversationId(conversationId: Long) {
        messageDao.deleteMessagesByConversationId(conversationId)
    }
}
