package com.malharang.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.malharang.app.data.local.entity.ExportSentenceEntity

@Dao
interface ExportSentenceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sentence: ExportSentenceEntity)

    @Query("SELECT * FROM export_sentence ORDER BY saved_at DESC")
    suspend fun getAll(): List<ExportSentenceEntity>

    @Delete
    suspend fun delete(sentence: ExportSentenceEntity)
}
