package com.malharang.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "export_sentence")
data class ExportSentenceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sentence: String,
    val translation: String,
    @ColumnInfo(name = "saved_at") val savedAt: Long
)
