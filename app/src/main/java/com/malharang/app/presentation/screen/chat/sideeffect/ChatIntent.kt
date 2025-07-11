package com.malharang.app.presentation.screen.chat.sideeffect

sealed interface ChatIntent {
    data class SendMessage(val message: String) : ChatIntent
    data class OnInputChanged(val input: String) : ChatIntent
    object ReceiveBotResponse : ChatIntent
    object OnVoiceClick : ChatIntent
}
