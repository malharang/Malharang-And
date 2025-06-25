package com.malharang.app.data.mapper.todomain

import com.malharang.app.data.remote.dto.response.STTResponseDto
import com.malharang.app.domain.model.STTData

fun STTResponseDto?.toDomain(): STTData = STTData(
    text = this?.text.orEmpty()
)