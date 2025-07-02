package com.malharang.app.data.local.datasourceimpl

import com.malharang.app.data.local.dao.ConversationDao
import com.malharang.app.data.local.datasource.ConversationLocalDataSource
import com.malharang.app.data.local.entity.ConversationEntity
import javax.inject.Inject

class ConversationLocalDataSourceImpl @Inject constructor(
    private val conversationDao: ConversationDao
) : ConversationLocalDataSource {

    override suspend fun insertConversation(conversation: ConversationEntity): Long {
        return conversationDao.insertConversation(conversation)
    }

    override suspend fun getConversationById(id: Long): ConversationEntity? {
        return conversationDao.getConversationById(id = id)
    }

    override suspend fun getAllConversations(): List<ConversationEntity> {
        return conversationDao.getAllConversations()
    }

    override suspend fun deleteConversation(conversation: ConversationEntity) {
        conversationDao.deleteConversation(conversation)
    }

    override suspend fun updateConversationMode(id: Long, mode: String) {
        conversationDao.updateMode(id = id, mode = mode)
    }
}
