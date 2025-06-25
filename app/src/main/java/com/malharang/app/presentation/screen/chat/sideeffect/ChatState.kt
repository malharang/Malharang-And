package com.malharang.app.presentation.screen.chat.sideeffect

import com.malharang.app.presentation.model.ChatMessage

data class ChatState(
    val chatList: List<ChatMessage> = emptyList(),
    val input: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isVoiced: Boolean = false,
)
