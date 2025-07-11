package com.malharang.app.data.remote.dto.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatStateDto(
    @SerialName("mode")
    val mode: String,

    @SerialName("selected_location")
    val selectedLocation: String,

    @SerialName("selected_scenario")
    val selectedScenario: String,

    @SerialName("messages")
    val messages: List<ChatMessageDto>
)

@Serializable
data class ChatMessageDto(
    @SerialName("role")
    val role: String,
    @SerialName("content")
    val content: String
)
