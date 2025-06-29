package com.malharang.app.domain.model

data class  ConversationData(
    val id: Long = 0,
    val mode: String,
    val selectedLocation: String,
    val selectedScenario: String,
    val messages: List<MessageData>
)

data class MessageData(
    val id: Long = 0,
    val conversationId: Long = 0,
    val role: String,
    val content: String,
    val passed: Boolean? = null,
    val commentContextuality: String? = null,
    val commentLexicalVariety: String? = null
)
