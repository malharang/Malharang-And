package com.malharang.app.data.remote.datasourceimpl

import com.malharang.app.data.remote.datasource.SpeechRemoteDataSource
import com.malharang.app.data.remote.dto.request.TTSRequestDto
import com.malharang.app.data.remote.dto.response.STTResponseDto
import com.malharang.app.data.remote.service.SpeechService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class SpeechRemoteDataSourceImpl @Inject constructor(
    private val speechService: SpeechService
) : SpeechRemoteDataSource {

    override suspend fun postSpeechToText(
        file: File
    ): STTResponseDto {
        val modelId = "scribe_v1".toRequestBody("text/plain".toMediaType())
        val languageCode = "ko".toRequestBody("text/plain".toMediaType())
        val requestFile = file.asRequestBody("audio/m4a".toMediaType())
        val multipartFile = MultipartBody.Part.createFormData(
            name = "file",
            filename = file.name,
            body = requestFile
        )

        return speechService.postSpeechToText(
            modelId = modelId,
            languageCode = languageCode,
            file = multipartFile
        )
    }

    override suspend fun postTextToSpeech(text: String): Response<ResponseBody> =
        speechService.postTextToSpeech(
            body = TTSRequestDto(text = text)
        )
}
