package com.malharang.app.presentation.model

data class ChatMessageModel(
    val text: String,
    val sender: SenderType,
    val translatedText: String? = null,
    val isTranslating: Boolean = false,
    val isSoundPlaying: Boolean = false
)

enum class SenderType {
    BOT,
    USER
}
