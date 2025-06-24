package com.malharang.app.domain.usecase

import com.malharang.app.domain.model.TranslateData
import com.malharang.app.domain.repository.TranslateRepository
import javax.inject.Inject

class TranslateUseCase @Inject constructor(
    private val translateRepository: TranslateRepository
) {
    suspend operator fun invoke(
        text: String,
        language: String
    ): Result<TranslateData> = translateRepository.getTranslate(
        text = text,
        language = language
    )
}
