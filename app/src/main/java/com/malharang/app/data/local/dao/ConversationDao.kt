package com.malharang.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.malharang.app.data.local.entity.ConversationEntity

@Dao
interface ConversationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConversation(conversation: ConversationEntity): Long

    @Query("SELECT * FROM conversation WHERE id = :id")
    suspend fun getConversationById(id: Long): ConversationEntity?

    @Query("SELECT * FROM conversation ORDER BY id DESC")
    suspend fun getAllConversations(): List<ConversationEntity>

    @Delete
    suspend fun deleteConversation(conversation: ConversationEntity)
}