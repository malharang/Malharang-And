package com.malharang.app.data.remote.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class EvaluationRequestDto(
    val messages: List<MessageDto>
)

@Serializable
data class MessageDto(
    val role: String,
    val content: String
)