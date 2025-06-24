package com.malharang.app.data.repositoryimpl

import com.malharang.app.data.mapper.todomain.toDomain
import com.malharang.app.data.remote.datasource.TranslateRemoteDataSource
import com.malharang.app.domain.model.TranslateData
import com.malharang.app.domain.repository.TranslateRepository
import javax.inject.Inject

class TranslateRepositoryImpl @Inject constructor(
    private val translateRemoteDataSource: TranslateRemoteDataSource
) : TranslateRepository {

    override suspend fun getTranslate(
        text: String,
        language: String
    ): Result<TranslateData> = runCatching {
        translateRemoteDataSource.getTranslate(
            text = text,
            language = language
        ).data.toDomain()
    }
}
