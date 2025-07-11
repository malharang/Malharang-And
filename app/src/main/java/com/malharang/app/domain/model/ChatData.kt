package com.malharang.app.domain.model

data class ChatData(
    val reply: String,
    val state: ChatStateData
)

data class ChatStateData(
    val mode: String,
    val selectedLocation: String,
    val selectedScenario: String,
    val messages: List<ChatMessageData>
)

data class ChatMessageData(
    val role: String,
    val content: String
)
