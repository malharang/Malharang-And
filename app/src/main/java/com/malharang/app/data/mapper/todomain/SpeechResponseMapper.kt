package com.malharang.app.data.mapper.todomain

import com.malharang.app.data.remote.dto.response.STTResponseDto
import com.malharang.app.domain.model.STTData
import com.malharang.app.domain.model.TTSData
import okhttp3.ResponseBody
import retrofit2.Response

fun STTResponseDto?.toDomain(): STTData = STTData(
    text = this?.text.orEmpty()
)

fun Response<ResponseBody>?.toDomain(): TTSData? =
    this?.body()?.let { TTSData(audioStream = it) }
