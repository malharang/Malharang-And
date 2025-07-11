package com.malharang.app.data.local.dao

import androidx.room.*
import com.malharang.app.data.local.entity.MessageEntity

@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: MessageEntity): Long

    @Query("SELECT * FROM message WHERE conversation_id = :conversationId ORDER BY id ASC")
    suspend fun getMessagesByConversationId(conversationId: Long): List<MessageEntity>

    @Delete
    suspend fun deleteMessage(message: MessageEntity)

    @Query("DELETE FROM message WHERE conversation_id = :conversationId")
    suspend fun deleteMessagesByConversationId(conversationId: Long)
}
