package com.malharang.app.domain.repository

import com.malharang.app.domain.model.STTData
import java.io.File

interface SpeechRepository {
    suspend fun postSpeechToText(file: File): Result<STTData>
}