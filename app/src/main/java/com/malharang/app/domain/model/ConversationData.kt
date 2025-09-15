package com.malharang.app.domain.model

data class ConversationData(
    val id: Long = 0,
    val mode: String,
    val selectedLocation: String,
    val selectedScenario: String
)

data class MessageData(
    val id: Long = 0,
    val conversationId: Long = 0,
    val role: String,
    val content: String,
    val contextualityPassed: Boolean? = null,
    val contextualityComment: String? = null,
    val grammarPassed: Boolean? = null,
    val grammarComment: String? = null,
    val grammarErrors: String? = null
)
