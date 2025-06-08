package com.malharang.app.presentation.screen.chat

sealed interface ChatSideEffect {
    data class ShowToast(val message: String) : ChatSideEffect
}
