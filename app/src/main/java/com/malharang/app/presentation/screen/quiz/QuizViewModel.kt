package com.malharang.app.presentation.screen.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malharang.app.data.local.datastore.ConversationDataStore
import com.malharang.app.domain.mapper.toChatMessageModelList
import com.malharang.app.domain.model.MessageData
import com.malharang.app.domain.model.QuizData
import com.malharang.app.domain.usecase.GetAllConversationsUseCase
import com.malharang.app.domain.usecase.GetConversationByIdUseCase
import com.malharang.app.domain.usecase.GetMessagesByConversationIdUseCase
import com.malharang.app.domain.usecase.QuizUseCase
import com.malharang.app.presentation.model.ChatMessageModel
import com.malharang.app.presentation.model.MissionCardModel
import com.malharang.app.presentation.screen.quiz.model.CountResult
import com.malharang.app.presentation.screen.quiz.model.QuizState
import com.malharang.app.presentation.screen.quiz.model.QuizStep
import com.malharang.app.presentation.screen.quiz.model.QuizType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val getAllConversationsUseCase: GetAllConversationsUseCase,
    private val quizUseCase: QuizUseCase,
    private val getMessageByConversationByIdUseCase: GetMessagesByConversationIdUseCase,
    ) : ViewModel() {

    private val _reviewMissions = MutableStateFlow<List<MissionCardModel>>(emptyList())
    val reviewMissions: StateFlow<List<MissionCardModel>> = _reviewMissions

    private val _state = MutableStateFlow(QuizState())
    val state: StateFlow<QuizState> = _state

    private val _quizList = MutableStateFlow<List<QuizData>>(emptyList())
    val quizList: StateFlow<List<QuizData>> = _quizList

    private val _messages = MutableStateFlow<List<ChatMessageModel>>(emptyList())
    val messages: StateFlow<List<ChatMessageModel>> = _messages

    fun updateStep(step: QuizStep) {
        _state.update { it.copy(currentStep = step) }
    }

    fun reset() {
        _state.value = QuizState()
    }

    fun selectScenario(scenarioId: Int) {
        _state.update { it.copy(selectedScenarioId = scenarioId) }
    }

    fun selectType(type: QuizType) {
        _state.update { it.copy(quizType = type) }
    }

    fun setResult(result: CountResult) {
        _state.update { it.copy(result = result) }
    }

    fun getFinishedConversations() {
        viewModelScope.launch {
            val allConversations = getAllConversationsUseCase()

            val review = allConversations
                .filter { it.mode == "finished" }
                .map {
                    MissionCardModel(
                        title = it.selectedScenario,
                        conversationId = it.id
                    )
                }

            _reviewMissions.value = review
        }
    }

    fun loadQuiz(quizType: String, messages: List<MessageData>) {
        viewModelScope.launch {
            quizUseCase(quizType, messages)
                .onSuccess { list ->
                    _quizList.value = list
                }
                .onFailure { throwable ->
                    // 에러 처리 로직 추가 가능
                }
        }
    }

    fun loadMessages(conversationId: Long, quizType: String) {
        viewModelScope.launch {
            val conversation = getMessageByConversationByIdUseCase(conversationId)

            val messages = getMessageByConversationByIdUseCase(conversationId)
            _messages.value = messages.toChatMessageModelList()

            loadQuiz(quizType, messages)
        }
    }

}

