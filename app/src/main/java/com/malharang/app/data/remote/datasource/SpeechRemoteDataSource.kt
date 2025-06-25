package com.malharang.app.data.remote.datasource

import com.malharang.app.data.remote.dto.response.STTResponseDto
import java.io.File

interface SpeechRemoteDataSource {
    suspend fun postSpeechToText(file: File): STTResponseDto
}