package com.malharang.app.presentation.screen.chat.sideeffect

sealed interface ChatSideEffect {
    data class ShowToast(val message: String) : ChatSideEffect
}
