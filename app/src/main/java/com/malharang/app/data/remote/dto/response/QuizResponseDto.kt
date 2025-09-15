package com.malharang.app.data.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuizResponseDto(
    @SerialName("data")
    val data: List<QuizItemDto>,

    @SerialName("success")
    val success: Boolean
)

@Serializable
data class QuizItemDto(
    @SerialName("question")
    val question: String,

    @SerialName("options")
    val options: List<String>,

    @SerialName("answer")
    val answer: Int, // 1-based index (e.g., 1 → 첫 번째 선택지)

    @SerialName("type")
    val type: String // "word", "sentence" 등
)
