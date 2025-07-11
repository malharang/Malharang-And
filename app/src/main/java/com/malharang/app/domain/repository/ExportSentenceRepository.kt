package com.malharang.app.domain.repository

import com.malharang.app.domain.model.ExportSentenceData

interface ExportSentenceRepository {
    suspend fun save(sentence: ExportSentenceData)
    suspend fun getAll(): List<ExportSentenceData>
    suspend fun delete(sentence: ExportSentenceData)
}
