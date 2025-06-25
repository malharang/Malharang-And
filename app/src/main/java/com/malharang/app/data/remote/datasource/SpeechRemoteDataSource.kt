package com.malharang.app.data.remote.datasource

import com.malharang.app.data.remote.dto.response.STTResponseDto
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.File

interface SpeechRemoteDataSource {
    suspend fun postSpeechToText(file: File): STTResponseDto

    suspend fun postTextToSpeech(text: String): Response<ResponseBody>
}