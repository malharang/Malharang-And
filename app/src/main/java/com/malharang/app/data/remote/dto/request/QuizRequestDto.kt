package com.malharang.app.data.remote.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuizRequestDto(
    @SerialName("quiz_type")
    val quizType: String, // "word" 또는 "sentence"

    @SerialName("messages")
    val messages: List<ChatMessageDto>
)

@Serializable
data class ChatMessageDto(
    @SerialName("role")
    val role: String, // "user" 또는 "assistant"

    @SerialName("content")
    val content: String
)
