package com.malharang.app.data.remote.service

import com.malharang.app.BuildConfig
import com.malharang.app.data.remote.dto.response.STTResponseDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface SpeechService {
    @Multipart
    @POST("v1/speech-to-text")
    suspend fun postSpeechToText(
        @Header("xi-api-key") apiKey: String = BuildConfig.SPEECH_API_KEY,
        @Part("model_id") modelId: RequestBody,
        @Part("language_code") languageCode: RequestBody,
        @Part file: MultipartBody.Part
    ): STTResponseDto
}

