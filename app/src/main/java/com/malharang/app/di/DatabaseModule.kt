package com.malharang.app.di

import android.content.Context
import androidx.room.Room
import com.malharang.app.data.local.AppDatabase
import com.malharang.app.data.local.dao.ConversationDao
import com.malharang.app.data.local.dao.ExportSentenceDao
import com.malharang.app.data.local.dao.MessageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "malharang_db"
        ).build()
    }

    @Provides
    fun provideConversationDao(db: AppDatabase): ConversationDao {
        return db.conversationDao()
    }

    @Provides
    fun provideMessageDao(db: AppDatabase): MessageDao {
        return db.messageDao()
    }

    @Provides
    fun provideExportSentenceDao(db: AppDatabase): ExportSentenceDao = db.ExportSentenceDao()
}
