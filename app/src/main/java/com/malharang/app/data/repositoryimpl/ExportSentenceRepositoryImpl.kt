package com.malharang.app.data.repositoryimpl

import com.malharang.app.data.local.dao.ExportSentenceDao
import com.malharang.app.data.mapper.todomain.toDomain
import com.malharang.app.data.mapper.toentity.toEntity
import com.malharang.app.domain.model.ExportSentenceData
import com.malharang.app.domain.repository.ExportSentenceRepository
import javax.inject.Inject

class ExportSentenceRepositoryImpl @Inject constructor(
    private val exportSentenceDao: ExportSentenceDao
) : ExportSentenceRepository {

    override suspend fun save(sentence: ExportSentenceData) {
        exportSentenceDao.insert(sentence.toEntity())
    }

    override suspend fun getAll(): List<ExportSentenceData> {
        return exportSentenceDao.getAll().map { it.toDomain() }
    }

    override suspend fun delete(sentence: ExportSentenceData) {
        exportSentenceDao.delete(sentence.toEntity())
    }
}
