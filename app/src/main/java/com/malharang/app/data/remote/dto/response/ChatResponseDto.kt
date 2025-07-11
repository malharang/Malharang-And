package com.malharang.app.data.remote.dto.response

import com.malharang.app.data.remote.dto.common.ChatStateDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatResponseDto(
    @SerialName("data")
    val data: ChatDataDto,

    @SerialName("message")
    val message: String,

    @SerialName("success")
    val success: Boolean
)

@Serializable
data class ChatDataDto(
    @SerialName("reply")
    val reply: String,

    @SerialName("state")
    val state: ChatStateDto
)
