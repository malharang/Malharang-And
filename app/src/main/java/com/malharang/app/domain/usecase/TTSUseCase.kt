package com.malharang.app.domain.usecase

import com.malharang.app.domain.model.TTSData
import com.malharang.app.domain.repository.SpeechRepository
import javax.inject.Inject

class TTSUseCase @Inject constructor(
    private val speechRepository: SpeechRepository
) {
    suspend operator fun invoke(text: String): Result<TTSData> =
        speechRepository.postTextToSpeech(text = text)
}