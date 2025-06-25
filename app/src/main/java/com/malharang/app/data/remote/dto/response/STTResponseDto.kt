package com.malharang.app.data.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class STTResponseDto(
    @SerialName("text")
    val text: String
)