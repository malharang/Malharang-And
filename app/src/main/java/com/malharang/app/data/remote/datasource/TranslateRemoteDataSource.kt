package com.malharang.app.data.remote.datasource

import com.malharang.app.data.remote.dto.response.TranslateResponseDTO

interface TranslateRemoteDataSource {
    suspend fun getTranslate(
        text: String,
        language: String
    ): TranslateResponseDTO
}
