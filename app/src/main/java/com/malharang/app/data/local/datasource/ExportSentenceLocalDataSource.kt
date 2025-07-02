package com.malharang.app.data.local.datasource

import com.malharang.app.data.local.entity.ExportSentenceEntity

interface ExportSentenceLocalDataSource {
    suspend fun insert(sentence: ExportSentenceEntity)
    suspend fun getAll(): List<ExportSentenceEntity>
    suspend fun delete(sentence: ExportSentenceEntity)
}
