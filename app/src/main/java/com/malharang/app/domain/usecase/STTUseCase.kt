package com.malharang.app.domain.usecase

import com.malharang.app.domain.model.STTData
import com.malharang.app.domain.repository.SpeechRepository
import java.io.File
import javax.inject.Inject

class STTUseCase @Inject constructor(
    private val speechRepository: SpeechRepository
) {
    suspend operator fun invoke(file: File): Result<STTData> =
        speechRepository.postSpeechToText(file = file)
}
