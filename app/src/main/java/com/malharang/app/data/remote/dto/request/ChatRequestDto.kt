package com.malharang.app.data.remote.dto.request

import com.malharang.app.data.remote.dto.common.ChatStateDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatRequestDto(
    @SerialName("user_input")
    val userInput: String,
    @SerialName("state")
    val state: ChatStateDto
)
