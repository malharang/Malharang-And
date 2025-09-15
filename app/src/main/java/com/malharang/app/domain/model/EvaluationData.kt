package com.malharang.app.domain.model

data class EvaluationRequestData(
    val messages: List<MessageData>
)

data class EvaluationMessageData(
    val role: String,
    val content: String
)

data class EvaluationResponseData(
    val data: EvaluationResultData,
    val message: String,
    val success: Boolean
)

data class EvaluationResultData(
    val contextuality: ContextualityData,
    val grammar: GrammarData
)

data class ContextualityData(
    val comment: String,
    val contextuality: List<String>,
    val pass: Boolean
)

data class GrammarData(
    val comment: String,
    val grammar: List<GrammarErrorData>,
    val pass: Boolean
)

data class GrammarErrorData(
    val originalSentence: String,
    val correctedSentence: String,
    val errorExplanation: String,
    val simpleExplanation: String
)
