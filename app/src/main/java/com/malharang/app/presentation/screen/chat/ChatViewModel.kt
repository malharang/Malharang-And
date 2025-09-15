package com.malharang.app.presentation.screen.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malharang.app.data.local.datastore.ConversationDataStore
import com.malharang.app.domain.mapper.toChatMessageDataList
import com.malharang.app.domain.mapper.toChatMessageModelList
import com.malharang.app.domain.mapper.toMessageData
import com.malharang.app.domain.model.ChatStateData
import com.malharang.app.domain.model.EvaluationRequestData
import com.malharang.app.domain.model.MessageData
import com.malharang.app.domain.usecase.ChatUseCase
import com.malharang.app.domain.usecase.EvaluationUseCase
import com.malharang.app.domain.usecase.GetConversationByIdUseCase
import com.malharang.app.domain.usecase.GetMessagesByConversationIdUseCase
import com.malharang.app.domain.usecase.InsertMessageUseCase
import com.malharang.app.domain.usecase.UpdateMessageUseCase
import com.malharang.app.domain.usecase.STTUseCase
import com.malharang.app.domain.usecase.SaveExportSentenceUseCase
import com.malharang.app.domain.usecase.TTSUseCase
import com.malharang.app.domain.usecase.TranslateUseCase
import com.malharang.app.domain.usecase.UpdateConversationModeUseCase
import com.malharang.app.presentation.model.ChatMessageModel
import com.malharang.app.presentation.model.SenderType
import com.malharang.app.presentation.screen.chat.component.SpeechRecorderManager
import com.malharang.app.presentation.screen.chat.sideeffect.ChatIntent
import com.malharang.app.presentation.screen.chat.sideeffect.ChatSideEffect
import com.malharang.app.presentation.screen.chat.sideeffect.ChatState
import com.malharang.app.presentation.screen.chat.sideeffect.MicState
import com.malharang.app.presentation.screen.chat.type.EvaluationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
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
    private val conversationIdDataStore: ConversationDataStore,
    private val translateUseCase: TranslateUseCase,
    private val recorder: SpeechRecorderManager,
    private val sttUseCase: STTUseCase,
    private val ttsUseCase: TTSUseCase,
    private val chatUseCase: ChatUseCase,
    private val getConversationByIdUseCase: GetConversationByIdUseCase,
    private val insertMessageUseCase: InsertMessageUseCase,
    private val getMessageByConversationByIdUseCase: GetMessagesByConversationIdUseCase,
    private val updateConversationModeUseCase: UpdateConversationModeUseCase,
    private val saveExportSentenceUseCase: SaveExportSentenceUseCase,
    private val evaluationUseCase: EvaluationUseCase,
    private val updateMessageUseCase: UpdateMessageUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ChatState())
    val state: StateFlow<ChatState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<ChatSideEffect>()
    val sideEffect: SharedFlow<ChatSideEffect> = _sideEffect

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _micState = MutableStateFlow(MicState.Idle)
    val micState: StateFlow<MicState> = _micState

    init {
        viewModelScope.launch {
            val id = conversationIdDataStore.getRecentConversationId()
            initConversation(id)
        }
    }

    private fun sendMessage(message: String) {
        val currentChat = _state.value.chatList + ChatMessageModel(message, SenderType.USER)
        _state.update { it.copy(chatList = currentChat, input = "", isLoading = true) }

        viewModelScope.launch {
            val conversationId = _state.value.chatId ?: return@launch

            val userMessageData = ChatMessageModel(
                text = message,
                sender = SenderType.USER
            ).toMessageData(conversationId)
            insertMessageUseCase(userMessageData)

            val conversation = getConversationByIdUseCase(conversationId) ?: return@launch
            val messages = getMessageByConversationByIdUseCase(conversationId)

            val state = ChatStateData(
                mode = conversation.mode,
                selectedLocation = conversation.selectedLocation,
                selectedScenario = conversation.selectedScenario,
                messages = messages.toChatMessageDataList()
            )

            postChat(
                userInput = message,
                state = state,
                conversationId = conversationId
            )
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
                if (_state.value.isVoiced) {
                    stopMicSwitchToChat()
                } else {
                    _state.update { it.copy(isVoiced = true) }
                }
            }
        }
    }

    private fun updateChatMessageAt(index: Int, update: (ChatMessageModel) -> ChatMessageModel) {
        _state.update { currentState ->
            val updatedList = currentState.chatList.toMutableList()
            val original = updatedList.getOrNull(index)

            if (original != null) {
                updatedList[index] = update(original)
            }

            currentState.copy(chatList = updatedList)
        }
    }

    fun initConversation(conversationId: Long?) {
        Timber.tag("POST_CHAT_STATE").d("conversationId: $conversationId")

        if (conversationId == null) {
            _state.update { it.copy(isInitialized = true) }

            return
        }

        viewModelScope.launch {
            val conversation = getConversationByIdUseCase(conversationId)

            if (conversation == null) {
                _errorMessage.value = "❌ 대화 정보를 불러올 수 없습니다."
                _state.update { it.copy(isInitialized = true) }
                return@launch
            }

            val messages = getMessageByConversationByIdUseCase(conversationId).toChatMessageModelList()

            val isScenarioInitialized = messages.isNotEmpty()

            _state.update {
                it.copy(
                    chatId = conversationId,
                    chatList = messages,
                    title = conversation.selectedScenario,
                    isInitialized = isScenarioInitialized
                )
            }

            if (!isScenarioInitialized) {
                postChat(
                    userInput = conversation.selectedScenario,
                    state = ChatStateData(
                        mode = conversation.mode,
                        selectedLocation = conversation.selectedLocation,
                        selectedScenario = conversation.selectedScenario,
                        messages = emptyList()
                    ),
                    conversationId = conversationId
                )
            } else {
                _state.update { it.copy(isInitialized = true) }
            }
        }
    }

    private fun postChat(
        userInput: String,
        state: ChatStateData,
        conversationId: Long
    ) {
        viewModelScope.launch {
            chatUseCase(
                userInput = userInput,
                state = state
            ).onSuccess { chatData ->
                val messageDb = getMessageByConversationByIdUseCase(conversationId)

                if (messageDb.isEmpty()) {
                    chatData.state.messages.firstOrNull { it.role == "system" }?.let { systemMsg ->
                        insertMessageUseCase(systemMsg.toMessageData(conversationId))
                    }
                }

                val assistantMessage = ChatMessageModel(
                    text = chatData.reply,
                    sender = SenderType.BOT
                )

                insertMessageUseCase(assistantMessage.toMessageData(conversationId))
                updateConversationModeUseCase(conversationId, chatData.state.mode)

                _state.update {
                    it.copy(
                        chatList = it.chatList + assistantMessage,
                        isLoading = false,
                        isInitialized = true
                    )
                }

                // 새로운 응답이 왔을 때 평가 시작
                fetchEvaluation(conversationId)
            }.onFailure { error ->
                _errorMessage.value = error.message
                _state.update {
                    it.copy(
                        isLoading = false,
                        isInitialized = true
                    )
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
                        _errorMessage.value = throwable.message
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

    fun postTextToSpeech(index: Int, text: String) {
        viewModelScope.launch {
            val isPlaying = _state.value.chatList.getOrNull(index)?.isSoundPlaying == true

            if (isPlaying) {
                recorder.stopTTS {
                    updateChatMessageAt(index) { it.copy(isSoundPlaying = false) }
                }
                return@launch
            }

            // 재생 시작 표시
            updateChatMessageAt(index) { it.copy(isSoundPlaying = true) }

            ttsUseCase(text = text)
                .onSuccess { ttsData ->
                    Timber.tag("TTS_TEST").d("TTS API 호출 성공")

                    recorder.playTTSStream(
                        responseBody = ttsData.audioStream,
                        onComplete = {
                            // 재생 완료 표시
                            updateChatMessageAt(index) { it.copy(isSoundPlaying = false) }
                        }
                    )
                }
                .onFailure { throwable ->
                    Timber.tag("TTS_TEST").e(throwable, "TTS API 호출 실패")
                    _errorMessage.value = throwable.message

                    updateChatMessageAt(index) { it.copy(isSoundPlaying = false) }
                }
        }
    }

    fun getTranslate(
        index: Int,
        text: String,
        language: String = "en",
        isArchive: Boolean = false
    ) {
        val currentMessage = _state.value.chatList.getOrNull(index)

        if (currentMessage?.translatedText != null) {
            if (isArchive) {
                saveSentence(text, currentMessage.translatedText)
            } else {
                toggleTranslationVisibility(index)
            }
            return
        }

        viewModelScope.launch {
            updateChatMessageAt(index) {
                it.copy(
                    isTranslating = true,
                    translatedText = null,
                    isTranslationVisible = !isArchive
                )
            }

            translateUseCase(text = text, language = language)
                .onSuccess { translateData ->
                    updateChatMessageAt(index) {
                        it.copy(
                            translatedText = translateData.translatedText,
                            isTranslating = false,
                            isTranslationVisible = !isArchive
                        )
                    }

                    if (isArchive) {
                        saveSentence(text, translateData.translatedText)
                    }
                }
                .onFailure { error ->
                    _errorMessage.value = error.message
                    updateChatMessageAt(index) { it.copy(isTranslating = false) }
                }
        }
    }

    fun saveSentence(sentence: String, translation: String) {
        viewModelScope.launch {
            saveExportSentenceUseCase(sentence, translation)
            _sideEffect.emit(ChatSideEffect.ShowToast(""))
        }
    }

    fun toggleTranslationVisibility(index: Int) {
        updateChatMessageAt(index) { it.copy(isTranslationVisible = !it.isTranslationVisible) }
    }

    fun clearToastErrorMessage() {
        _errorMessage.value = null
    }

    fun fetchEvaluation(conversationId: Long) {
        viewModelScope.launch {
            // 마지막 사용자 메시지의 인덱스 찾기
            val lastUserMessageIndex = _state.value.chatList.indexOfLast { it.sender == SenderType.USER }
            if (lastUserMessageIndex == -1) return@launch

            // 마지막 사용자 메시지에 Loading 상태 설정
            updateChatMessageAt(lastUserMessageIndex) {
                it.copy(evaluationState = EvaluationState.Loading)
            }

            evaluationUseCase(
                EvaluationRequestData(
                    messages = _state.value.chatList.map { it.toMessageData(conversationId) }
                )
            ).onSuccess { response ->
                val evaluationState = if (response.data.contextuality.pass) {
                    EvaluationState.PASS
                } else {
                    EvaluationState.NOT_PASS
                }
                // 마지막 사용자 메시지에 평가 결과와 데이터 설정
                updateChatMessageAt(lastUserMessageIndex) {
                    it.copy(
                        evaluationState = evaluationState,
                        evaluationData = response
                    )
                }

                // DB에 평가 데이터 저장 (USER 메시지만)
                val lastUserMessage = _state.value.chatList[lastUserMessageIndex]
                if (lastUserMessage.sender == SenderType.USER) {
                    val allMessages = getMessageByConversationByIdUseCase(conversationId)
                    val userMessageFromDb = allMessages.findLast { it.role == "user" && it.content == lastUserMessage.text }

                    userMessageFromDb?.let { dbMessage ->
                        val grammarErrorsJson = Json.encodeToString(response.data.grammar.grammar)

                        val updatedMessage = MessageData(
                            id = dbMessage.id,
                            conversationId = dbMessage.conversationId,
                            role = dbMessage.role,
                            content = dbMessage.content,
                            contextualityPassed = response.data.contextuality.pass,
                            contextualityComment = response.data.contextuality.comment,
                            grammarPassed = response.data.grammar.pass,
                            grammarComment = response.data.grammar.comment,
                            grammarErrors = grammarErrorsJson
                        )

                        updateMessageUseCase(updatedMessage)
                    }
                }
            }.onFailure { error ->
                // 마지막 사용자 메시지에 실패 상태 설정
                updateChatMessageAt(lastUserMessageIndex) {
                    it.copy(evaluationState = EvaluationState.NOT_PASS)
                }
                _errorMessage.value = error.message
            }
        }
    }
}
