package com.malharang.app.domain.model

data class EvaluationData(
    val messages: List<EvaluationMessageData>
)

data class EvaluationMessageData(
    val role: String,
    val content: String
)
