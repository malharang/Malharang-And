package com.malharang.app.presentation.screen.chat.sideeffect

enum class ChatUiState {
    INITIALIZING,
    EMPTY,
    CHATTING
}

val ChatState.uiState: ChatUiState
    get() = when {
        !isInitialized -> ChatUiState.INITIALIZING
        chatId == null -> ChatUiState.EMPTY
        else -> ChatUiState.CHATTING
    }
