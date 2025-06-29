package com.malharang.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.malharang.app.data.local.dao.ConversationDao
import com.malharang.app.data.local.dao.MessageDao
import com.malharang.app.data.local.entity.ConversationEntity
import com.malharang.app.data.local.entity.MessageEntity

@Database(
    entities = [ConversationEntity::class, MessageEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun conversationDao(): ConversationDao
    abstract fun messageDao(): MessageDao
}