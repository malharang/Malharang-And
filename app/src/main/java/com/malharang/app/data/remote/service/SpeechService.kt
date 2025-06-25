package com.malharang.app.data.remote.service

import com.malharang.app.BuildConfig
import com.malharang.app.data.remote.dto.request.TTSRequestDto
import com.malharang.app.data.remote.dto.response.STTResponseDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Streaming

interface SpeechService {
    @Multipart
    @POST("v1/speech-to-text")
    suspend fun postSpeechToText(
        @Header("xi-api-key") apiKey: String = BuildConfig.SPEECH_API_KEY,
        @Part("model_id") modelId: RequestBody,
        @Part("language_code") languageCode: RequestBody,
        @Part file: MultipartBody.Part
    ): STTResponseDto

    @Streaming
    @POST("v1/text-to-speech/{voice_id}")
    suspend fun postTextToSpeech(
        @Path("voice_id") voiceId: String = "mYk0rAapHek2oTw18z8x",
        @Body body: TTSRequestDto,
        @Header("xi-api-key") apiKey: String = BuildConfig.SPEECH_API_KEY,
        @Query("output_format") format: String = "mp3_44100_128"
    ): Response<ResponseBody>
}
