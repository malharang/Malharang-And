package com.malharang.app.presentation.model

data class ChatMessage(
    val text: String,
    val sender: SenderType
)

enum class SenderType {
    BOT,
    USER
}
