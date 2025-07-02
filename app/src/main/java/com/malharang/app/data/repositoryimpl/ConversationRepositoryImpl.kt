package com.malharang.app.data.repositoryimpl

import com.malharang.app.data.local.dao.ConversationDao
import com.malharang.app.data.local.entity.ConversationEntity
import com.malharang.app.domain.repository.ConversationRepository
import javax.inject.Inject

class ConversationRepositoryImpl @Inject constructor(
    private val conversationDao: ConversationDao
) : ConversationRepository {

    override suspend fun insertConversation(entity: ConversationEntity): Long {
        return conversationDao.insertConversation(entity)
    }

    override suspend fun getConversationById(id: Long): ConversationEntity? {
        return conversationDao.getConversationById(id)
    }

    override suspend fun getAllConversations(): List<ConversationEntity> {
        return conversationDao.getAllConversations()
    }

    override suspend fun deleteConversation(entity: ConversationEntity) {
        conversationDao.deleteConversation(entity)
    }

    override suspend fun updateConversationMode(id: Long, mode: String) {
        conversationDao.updateMode(id = id, mode = mode)
    }
}
