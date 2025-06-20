package com.malharang.app.presentation.model

data class ChatMessage(
    val text: String,
    val sender: SenderType,
    val translatedText: String? = null,
    val isTranslating: Boolean = false
)

enum class SenderType {
    BOT,
    USER
}
