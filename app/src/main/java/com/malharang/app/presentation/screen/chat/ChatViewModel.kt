package com.malharang.app.presentation.screen.chat

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malharang.app.domain.usecase.STTUseCase
import com.malharang.app.domain.usecase.TranslateUseCase
import com.malharang.app.presentation.model.ChatMessage
import com.malharang.app.presentation.screen.chat.sideeffect.MicState
import com.malharang.app.presentation.model.SenderType
import com.malharang.app.presentation.screen.chat.component.SpeechRecorderManager
import com.malharang.app.presentation.screen.chat.sideeffect.ChatIntent
import com.malharang.app.presentation.screen.chat.sideeffect.ChatSideEffect
import com.malharang.app.presentation.screen.chat.sideeffect.ChatState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val translateUseCase: TranslateUseCase,
    private val recorder: SpeechRecorderManager,
    private val sttUseCase: STTUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(ChatState())
    val state: StateFlow<ChatState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<ChatSideEffect>()
    val sideEffect: SharedFlow<ChatSideEffect> = _sideEffect

    private val _translateErrorMessage = MutableStateFlow<String?>(null)
    val translateErrorMessage: StateFlow<String?> = _translateErrorMessage.asStateFlow()

    private val _sttErrorMessage = MutableStateFlow<String?>(null)
    val sttErrorMessage: StateFlow<String?> = _sttErrorMessage.asStateFlow()

    private val _micState = MutableStateFlow(MicState.Idle)
    val micState: StateFlow<MicState> = _micState

    init {
        viewModelScope.launch {
            val botReply = ChatMessage("안녕하세요! 무엇을 도와드릴까요?", SenderType.BOT)
            val updatedChat = _state.value.chatList + botReply
            _state.update { it.copy(chatList = updatedChat, isLoading = false) }
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
                if(_state.value.isVoiced) {
                    stopMicSwitchToChat()
                } else {
                    _state.update { it.copy(isVoiced = true) }
                }
            }
        }
    }

    private var recordJob: Job? = null

    fun updateMicState(state: MicState) {
        _micState.value = state
    }

    fun onMicClicked() {
        when (_micState.value) {
            MicState.Idle -> startRecording()
            MicState.StartRecording -> stopRecording()
            else -> Unit
        }
    }

    private fun startRecording() {
        recordJob?.cancel()
        viewModelScope.launch {
            recorder.start()
            updateMicState(MicState.StartRecording)

            recordJob = launch {
                delay(60_000)
                stopRecording()
            }
        }
    }

    private fun stopRecording() {
        viewModelScope.launch {
            if (_micState.value != MicState.StartRecording) return@launch

            updateMicState(MicState.StartProcessing)
            delay(300)

            val path = recorder.stop()
            recordJob?.cancel()

            // path → STT API 업로드 처리
            postSpeechToText(File(path))
            delay(200)

            updateMicState(MicState.EndRecording)
            delay(200)
            updateMicState(MicState.Idle)
        }
    }

    fun postSpeechToText(file: File) {
        viewModelScope.launch {
            try {
                sttUseCase(file = file)
                    .onSuccess { sttData ->
                        Timber.tag("STT_TEST").d("STT API 호출 성공: ${sttData.text}")
                        sendMessage(sttData.text)
                    }
                    .onFailure { throwable ->
                        Timber.tag("STT_TEST").e(throwable, "STT API 호출 실패")
                        _sttErrorMessage.value = throwable.message
                    }
            } finally {
                if (file.exists()) {
                    file.delete()
                    Timber.tag("STT_TEST").d("녹음 파일 삭제 완료: ${file.name}")
                }
            }
        }
    }

    private fun stopMicSwitchToChat() {
        viewModelScope.launch {
            if (_micState.value == MicState.StartRecording) {
                updateMicState(MicState.StartProcessing)
                recorder.stop()
                recordJob?.cancel()

                updateMicState(MicState.EndRecording)

                updateMicState(MicState.Idle)
            } else {
                updateMicState(MicState.Idle)
            }

            _state.update { it.copy(isVoiced = false) }
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

    fun clearToastSTTErrorMessage() {
        _sttErrorMessage.value = null
    }

}
