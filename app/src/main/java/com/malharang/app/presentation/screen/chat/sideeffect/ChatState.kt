package com.malharang.app.presentation.screen.chat.sideeffect

import com.malharang.app.presentation.model.ChatMessageModel

data class ChatState(
    val chatId: Long? = null,
    val chatList: List<ChatMessageModel> = emptyList(),
    val input: String = "",
    val title: String = "",
    val isLoading: Boolean = false, // Message Loading
    val error: String? = null,
    val isVoiced: Boolean = false,
    val isInitialized: Boolean = false
)
