package com.malharang.app.domain.repository

import com.malharang.app.domain.model.STTData
import com.malharang.app.domain.model.TTSData
import java.io.File

interface SpeechRepository {
    suspend fun postSpeechToText(file: File): Result<STTData>

    suspend fun postTextToSpeech(text: String): Result<TTSData>
}
