package com.malharang.app.presentation.screen.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malharang.app.domain.usecase.TranslateUseCase
import com.malharang.app.presentation.model.ChatMessage
import com.malharang.app.presentation.model.MicState
import com.malharang.app.presentation.model.SenderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val translateUseCase: TranslateUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ChatState())
    val state: StateFlow<ChatState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<ChatSideEffect>()
    val sideEffect: SharedFlow<ChatSideEffect> = _sideEffect

    private val _translateErrorMessage = MutableStateFlow<String?>(null)
    val translateErrorMessage: StateFlow<String?> = _translateErrorMessage.asStateFlow()

    private val _micState = MutableStateFlow(MicState.Idle)
    val micState: StateFlow<MicState> = _micState

    init {
        viewModelScope.launch {
            val botReply = ChatMessage("안녕하세요! 무엇을 도와드릴까요?", SenderType.BOT)
            val updatedChat = _state.value.chatList + botReply
            _state.update { it.copy(chatList = updatedChat, isLoading = false) }
        }
    }

    fun onIntent(intent: ChatIntent) {
        when (intent) {
            is ChatIntent.OnInputChanged -> {
                _state.update { it.copy(input = intent.input) }
            }

            is ChatIntent.SendMessage -> {
                sendMessage(intent.message)
            }

            is ChatIntent.ReceiveBotResponse -> {
                // TODO: Receive bot response
            }

            is ChatIntent.OnVoiceClick -> {
                _state.update { it.copy(isVoiced = !it.isVoiced) }
            }
        }
    }

    private fun sendMessage(message: String) {
        val currentChat = _state.value.chatList + ChatMessage(message, SenderType.USER)
        _state.update { it.copy(chatList = currentChat, input = "", isLoading = true) }

        viewModelScope.launch {
            delay(1000)

            val botReply = ChatMessage("안녕하세요! 무엇을 도와드릴까요?", SenderType.BOT)
            val updatedChat = _state.value.chatList + botReply
            _state.update { it.copy(chatList = updatedChat, isLoading = false) }
        }
    }

    fun getTranslate(index: Int, text: String, language: String = "en") {
        viewModelScope.launch {
            _state.update { currentState ->
                val updatedList = currentState.chatList.toMutableList()
                val original = updatedList.getOrNull(index)
                if (original != null) {
                    updatedList[index] = original.copy(
                        isTranslating = true,
                        translatedText = null
                    )
                }
                currentState.copy(chatList = updatedList)
            }

            translateUseCase(
                text = text,
                language = language
            )
                .onSuccess { translateData ->

                    _state.update { currentState ->
                        val updatedList = currentState.chatList.toMutableList()
                        val original = updatedList.getOrNull(index)

                        if (original != null) {
                            updatedList[index] = original.copy(
                                translatedText = translateData.translatedText,
                                isTranslating = false
                            )
                        }

                        currentState.copy(chatList = updatedList)
                    }
                }
                .onFailure {
                    _translateErrorMessage.value = translateErrorMessage.toString()
                    _state.update { currentState ->
                        val updatedList = currentState.chatList.toMutableList()
                        val original = updatedList.getOrNull(index)
                        if (original != null) {
                            updatedList[index] = original.copy(isTranslating = false)
                        }
                        currentState.copy(chatList = updatedList)
                    }
                }
        }
    }

    fun clearToastTranslateErrorMessage() {
        _translateErrorMessage.value = null
    }

    fun updateMicState(state: MicState) {
        _micState.value = state
    }
}
