package com.malharang.app.data.local.datasourceimpl

import com.malharang.app.data.local.dao.ExportSentenceDao
import com.malharang.app.data.local.datasource.ExportSentenceLocalDataSource
import com.malharang.app.data.local.entity.ExportSentenceEntity
import javax.inject.Inject

class ExportSentenceLocalDataSourceImpl @Inject constructor(
    private val dao: ExportSentenceDao
) : ExportSentenceLocalDataSource {

    override suspend fun insert(sentence: ExportSentenceEntity) {
        dao.insert(sentence)
    }

    override suspend fun getAll(): List<ExportSentenceEntity> {
        return dao.getAll()
    }

    override suspend fun delete(sentence: ExportSentenceEntity) {
        dao.delete(sentence)
    }
}
