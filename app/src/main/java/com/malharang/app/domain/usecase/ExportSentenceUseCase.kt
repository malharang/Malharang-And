package com.malharang.app.domain.usecase

import com.malharang.app.domain.model.ExportSentenceData
import com.malharang.app.domain.repository.ExportSentenceRepository
import javax.inject.Inject

class SaveExportSentenceUseCase @Inject constructor(
    private val repository: ExportSentenceRepository
) {
    suspend operator fun invoke(sentence: String, translation: String) {
        val export = ExportSentenceData(
            sentence = sentence,
            translation = translation,
            savedAt = System.currentTimeMillis()
        )
        repository.save(export)
    }
}

class GetExportSentencesUseCase @Inject constructor(
    private val repository: ExportSentenceRepository
) {
    suspend operator fun invoke(): List<ExportSentenceData> {
        return repository.getAll()
    }
}

class DeleteExportSentenceUseCase @Inject constructor(
    private val repository: ExportSentenceRepository
) {
    suspend operator fun invoke(sentence: ExportSentenceData) {
        repository.delete(sentence)
    }
}
