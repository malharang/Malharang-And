package com.malharang.app.presentation.screen.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malharang.app.domain.mapper.toChatMessageModelList
import com.malharang.app.domain.model.MessageData
import com.malharang.app.domain.usecase.GetAllConversationsUseCase
import com.malharang.app.domain.usecase.GetMessagesByConversationIdUseCase
import com.malharang.app.domain.usecase.QuizUseCase
import com.malharang.app.presentation.model.ChatMessageModel
import com.malharang.app.presentation.model.MissionCardModel
import com.malharang.app.presentation.model.PlaceTypeItem
import com.malharang.app.presentation.screen.quiz.QuizContract.QuizSideEffect
import com.malharang.app.presentation.screen.quiz.QuizContract.QuizUiState
import com.malharang.app.presentation.screen.quiz.model.CountResult
import com.malharang.app.presentation.screen.quiz.model.QuizStep
import com.malharang.app.presentation.screen.quiz.model.QuizType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val getAllConversationsUseCase: GetAllConversationsUseCase,
    private val quizUseCase: QuizUseCase,
    private val getMessagesByConversationIdUseCase: GetMessagesByConversationIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<QuizSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun selectQuizType(type: QuizType) {
        _uiState.update { currentState ->
            currentState.copy(selectedType = type)
        }

        val currentState = _uiState.value
        val selectedConversation = currentState.selectedConversation

        if (selectedConversation?.conversationId != null) {
            loadChatMessagesForQuiz(selectedConversation.conversationId, type)
        }
    }

    fun loadConversations() {
        viewModelScope.launch {
            updateIsLoading(true)
            try {
                val conversations = getAllConversationsUseCase()
                val finishedConversations = conversations
                    .filter { it.mode == "finished" }
                    .map {
                        MissionCardModel(
                            title = it.selectedScenario,
                            type = PlaceTypeItem.Location(it.selectedLocation),
                            conversationId = it.id
                        )
                    }

                _uiState.update { currentState ->
                    currentState.copy(conversations = finishedConversations.toImmutableList())
                }
            } catch (e: Exception) {
                updateErrorMessage("Failed to load conversations: ${e.localizedMessage}")
            }
            updateIsLoading(false)
        }
    }

    fun selectConversation(conversation: MissionCardModel) {
        _uiState.update { currentState ->
            currentState.copy(selectedConversation = conversation)
        }
    }

    fun loadChatMessagesForQuiz(conversationId: Long, quizType: QuizType) {
        viewModelScope.launch {
            updateQuizLoadingState(QuizContract.QuizLoadingState.LOADING)
            try {
                val messages = getMessagesByConversationIdUseCase(conversationId)
                val chatMessages = messages.toChatMessageModelList()

                _uiState.update { currentState ->
                    currentState.copy(chatMessages = chatMessages.toImmutableList())
                }

                // 퀴즈 생성
                generateQuizWithConversation(chatMessages, quizType)
            } catch (e: Exception) {
                updateErrorMessage("Failed to load chat messages: ${e.localizedMessage}")
                updateQuizLoadingState(QuizContract.QuizLoadingState.ERROR)
            }
        }
    }

    private fun generateQuizWithConversation(chatMessages: List<ChatMessageModel>, quizType: QuizType) {
        val messageDataList = chatMessages.map {
            MessageData(
                role = when (it.sender) {
                    com.malharang.app.presentation.model.SenderType.BOT -> "assistant"
                    com.malharang.app.presentation.model.SenderType.USER -> "user"
                },
                content = it.text
            )
        }

        generateQuizByType(quizType.typeLabel, messageDataList)

        // 퀴즈 플레이 화면으로 이동
        _uiState.update { currentState ->
            currentState.copy(currentStep = QuizStep.PLAYING)
        }
        viewModelScope.launch {
            _sideEffect.emit(QuizSideEffect.NavigateToNextStep(QuizStep.PLAYING))
        }
    }

    private fun generateQuizByType(quizType: String, messages: List<MessageData>) {
        viewModelScope.launch {
            updateQuizLoadingState(QuizContract.QuizLoadingState.LOADING)
            updateErrorMessage(null) // 이전 에러 메시지 초기화

            try {
                quizUseCase(quizType, messages)
                    .onSuccess { quizList ->
                        if (quizList.isEmpty()) {
                            updateErrorMessage("생성된 퀴즈가 없습니다. 다른 대화를 선택해주세요.")
                            updateQuizLoadingState(QuizContract.QuizLoadingState.ERROR)
                        } else {
                            _uiState.update { currentState ->
                                currentState.copy(
                                    quizQuestions = quizList.toImmutableList(),
                                    currentQuestionIndex = 0,
                                    selectedAnswerIndex = null,
                                    userAnswers = emptyList<Int>().toImmutableList(),
                                    score = 0
                                )
                            }
                            updateQuizLoadingState(QuizContract.QuizLoadingState.QUESTION)
                        }
                    }.onFailure { exception ->
                        val errorMessage = when {
                            exception.message?.contains("시간이 오래 걸리고") == true ->
                                "퀴즈 생성에 시간이 오래 걸리고 있습니다.\n잠시 후 다시 시도해주세요."

                            exception.message?.contains("네트워크 연결") == true ->
                                "네트워크 연결을 확인해주세요."

                            else ->
                                "퀴즈 생성 중 오류가 발생했습니다.\n잠시 후 다시 시도해주세요."
                        }
                        updateErrorMessage(errorMessage)
                        updateQuizLoadingState(QuizContract.QuizLoadingState.ERROR)
                    }
            } catch (e: Exception) {
                val errorMessage = when {
                    e.message?.contains("timeout") == true || e.message?.contains("시간이 오래 걸리고") == true ->
                        "퀴즈 생성에 시간이 오래 걸리고 있습니다.\n잠시 후 다시 시도해주세요."

                    else ->
                        "퀴즈 생성 중 오류가 발생했습니다.\n잠시 후 다시 시도해주세요."
                }
                updateErrorMessage(errorMessage)
                updateQuizLoadingState(QuizContract.QuizLoadingState.ERROR)
            }
        }
    }

    fun selectAnswer(answerIndex: Int) {
        _uiState.update { currentState ->
            currentState.copy(selectedAnswerIndex = answerIndex)
        }
    }

    fun submitAnswer() {
        val currentState = _uiState.value
        val quizQuestions = currentState.quizQuestions
        if (quizQuestions.isEmpty()) return

        val selectedIndex = currentState.selectedAnswerIndex ?: return

        val currentQuestion = quizQuestions[currentState.currentQuestionIndex]
        val correctAnswerIndex = currentQuestion.answerIndex

        val isCorrect = (selectedIndex == correctAnswerIndex)

        val newScore = if (isCorrect) currentState.score + 1 else currentState.score
        val newAnswers = currentState.userAnswers + selectedIndex

        _uiState.update { state ->
            state.copy(
                userAnswers = newAnswers.toImmutableList(),
                score = newScore,
                selectedAnswerIndex = null
            )
        }

        updateQuizLoadingState(QuizContract.QuizLoadingState.RESULT)
    }

    fun nextQuestion() {
        val currentState = _uiState.value
        val quizQuestions = currentState.quizQuestions
        if (quizQuestions.isEmpty()) return
        val nextIndex = currentState.currentQuestionIndex + 1

        if (nextIndex < quizQuestions.size) {
            _uiState.update { currentState ->
                currentState.copy(currentQuestionIndex = nextIndex)
            }
            updateQuizLoadingState(QuizContract.QuizLoadingState.QUESTION)
        } else {
            completeQuiz()
        }
    }

    private fun completeQuiz() {
        val currentState = _uiState.value
        val quizQuestions = currentState.quizQuestions
        if (quizQuestions.isEmpty()) return

        val countResult = CountResult(
            total = quizQuestions.size,
            correct = currentState.score
        )

        _uiState.update { state ->
            state.copy(
                countResult = countResult,
                currentStep = QuizStep.RESULT
            )
        }

        viewModelScope.launch {
            _sideEffect.emit(QuizSideEffect.NavigateToResult(currentState.score, quizQuestions.size))
        }
    }

    fun resetQuiz() {
        _uiState.update { currentState ->
            currentState.copy(
                currentStep = QuizStep.START,
                selectedType = null,
                selectedConversation = null,
                quizQuestions = emptyList<com.malharang.app.domain.model.QuizData>().toImmutableList(),
                currentQuestionIndex = 0,
                selectedAnswerIndex = null,
                userAnswers = emptyList<Int>().toImmutableList(),
                score = 0,
                countResult = null,
                quizLoadingState = QuizContract.QuizLoadingState.LOADING
            )
        }
    }

    private fun updateQuizLoadingState(state: QuizContract.QuizLoadingState) {
        _uiState.update { currentState ->
            currentState.copy(quizLoadingState = state)
        }
    }

    private fun updateIsLoading(isLoading: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(isLoading = isLoading)
        }
    }

    private fun updateErrorMessage(message: String?) {
        _uiState.update { currentState ->
            currentState.copy(errorMessage = message)
        }
        message?.let {
            viewModelScope.launch {
                _sideEffect.emit(QuizSideEffect.ShowToast(it))
            }
        }
    }
}
