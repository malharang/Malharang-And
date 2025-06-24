package com.malharang.app.domain.repository

import com.malharang.app.domain.model.TranslateData

interface TranslateRepository {
    suspend fun getTranslate(
        text: String,
        language: String
    ): Result<TranslateData>
}
