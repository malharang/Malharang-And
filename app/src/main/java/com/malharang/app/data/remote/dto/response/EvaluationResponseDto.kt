package com.malharang.app.data.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EvaluationResponseDto(
    @SerialName("data") val data: EvaluationDataDto,
    @SerialName("message") val message: String,
    @SerialName("success") val success: Boolean
)

@Serializable
data class EvaluationDataDto(
    @SerialName("contextuality") val contextuality: ContextualityDto,
    @SerialName("grammar") val grammar: GrammarDto
)

@Serializable
data class ContextualityDto(
    @SerialName("comment") val comment: String,
    @SerialName("contextuality") val contextuality: List<ContextualityItemDto>,
    @SerialName("pass") val pass: Boolean
)

@Serializable
data class ContextualityItemDto(
    @SerialName("original sentence") val originalSentence: String,
    @SerialName("suggested sentence") val suggestedSentence: List<String>
)

@Serializable
data class GrammarDto(
    @SerialName("comment") val comment: String,
    @SerialName("grammar") val grammar: List<GrammarItemDto>,
    @SerialName("pass") val pass: Boolean
)

@Serializable
data class GrammarItemDto(
    @SerialName("corrected sentence") val correctedSentence: String,
    @SerialName("error explanation") val errorExplanation: String,
    @SerialName("original sentence") val originalSentence: String,
    @SerialName("simple explanation") val simpleExplanation: String
)