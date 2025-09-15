package com.malharang.app.presentation.screen.chat

import androidx.compose.runtime.Immutable
import com.malharang.app.presentation.model.ChatMessageModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

class ChatContract {
    @Immutable
    data class ChatUiState(
        val messages: ImmutableList<ChatMessageModel> = persistentListOf(),
        val currentMessage: String = "",
        val isLoading: Boolean = false,
        val isRecording: Boolean = false,
        val isSpeaking: Boolean = false,
        val errorMessage: String? = null,
        val conversationId: Long? = null,
        val isTyping: Boolean = false
    )

    sealed interface ChatSideEffect {
        data class ShowToast(val message: String) : ChatSideEffect
        data object ScrollToBottom : ChatSideEffect
        data object StartRecording : ChatSideEffect
        data object StopRecording : ChatSideEffect
        data object StartTTS : ChatSideEffect
        data object StopTTS : ChatSideEffect
        data class NavigateToQuizStart(val conversationId: Long) : ChatSideEffect
    }
}