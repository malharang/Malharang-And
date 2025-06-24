package com.malharang.app.data.remote.datasourceimpl

import com.malharang.app.data.remote.datasource.TranslateRemoteDataSource
import com.malharang.app.data.remote.dto.response.TranslateResponseDTO
import com.malharang.app.data.remote.service.TranslateService
import javax.inject.Inject

class TranslateRemoteDataSourceImpl @Inject constructor(
    private val translateService: TranslateService
) : TranslateRemoteDataSource {
    override suspend fun getTranslate(
        text: String,
        language: String
    ): TranslateResponseDTO =
        translateService.getTranslate(
            text = text,
            language = language
        )
}
