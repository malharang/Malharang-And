package com.malharang.app.core.local.room

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE message DROP COLUMN passed")
            database.execSQL("ALTER TABLE message DROP COLUMN commentContextuality")
            database.execSQL("ALTER TABLE message DROP COLUMN commentLexicalVariety")
            database.execSQL("ALTER TABLE message ADD COLUMN contextualityPassed INTEGER")
            database.execSQL("ALTER TABLE message ADD COLUMN contextualityComment TEXT")
            database.execSQL("ALTER TABLE message ADD COLUMN grammarPassed INTEGER")
            database.execSQL("ALTER TABLE message ADD COLUMN grammarComment TEXT")
            database.execSQL("ALTER TABLE message ADD COLUMN grammarErrors TEXT")
        }
    }

    private val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE message ADD COLUMN contextualityErrors TEXT")
        }
    }

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "malharang_db"
        ).addMigrations(MIGRATION_1_2, MIGRATION_2_3)
            .build()
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
