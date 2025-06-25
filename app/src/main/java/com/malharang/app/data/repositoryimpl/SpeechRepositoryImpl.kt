package com.malharang.app.data.repositoryimpl

import com.malharang.app.data.mapper.todomain.toDomain
import com.malharang.app.data.remote.datasource.SpeechRemoteDataSource
import com.malharang.app.domain.model.STTData
import com.malharang.app.domain.model.TTSData
import com.malharang.app.domain.repository.SpeechRepository
import java.io.File
import javax.inject.Inject

class SpeechRepositoryImpl @Inject constructor(
    private val speechRemoteDataSource: SpeechRemoteDataSource
) : SpeechRepository {
    override suspend fun postSpeechToText(file: File): Result<STTData> =
        runCatching {
            speechRemoteDataSource.postSpeechToText(file).toDomain()
        }

    override suspend fun postTextToSpeech(text: String): Result<TTSData> =
        runCatching {
            speechRemoteDataSource.postTextToSpeech(text = text).toDomain()
                ?: throw IllegalStateException("TTS 응답이 null입니다.")
        }
}
