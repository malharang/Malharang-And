package com.malharang.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.malharang.app.data.local.dao.ConversationDao
import com.malharang.app.data.local.dao.ExportSentenceDao
import com.malharang.app.data.local.dao.MessageDao
import com.malharang.app.data.local.entity.ConversationEntity
import com.malharang.app.data.local.entity.ExportSentenceEntity
import com.malharang.app.data.local.entity.MessageEntity

@Database(
    entities = [ConversationEntity::class, MessageEntity::class, ExportSentenceEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun conversationDao(): ConversationDao
    abstract fun messageDao(): MessageDao
    abstract fun ExportSentenceDao(): ExportSentenceDao
}
