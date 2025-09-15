package com.malharang.app.presentation.screen.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malharang.app.data.local.datastore.ConversationDataStore
import com.malharang.app.domain.mapper.toChatMessageModelList
import com.malharang.app.domain.usecase.GetAllConversationsUseCase
import com.malharang.app.domain.usecase.GetConversationByIdUseCase
import com.malharang.app.domain.usecase.GetMessagesByConversationIdUseCase
import com.malharang.app.domain.usecase.QuizUseCase
import com.malharang.app.presentation.model.MissionCardModel
import com.malharang.app.presentation.model.PlaceTypeItem
import com.malharang.app.presentation.screen.quiz.QuizContract.QuizSideEffect
import com.malharang.app.presentation.screen.quiz.QuizContract.QuizUiState
import com.malharang.app.presentation.screen.quiz.model.CountResult
import com.malharang.app.presentation.screen.quiz.model.QuizState
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
    private val conversationDataStore: ConversationDataStore,
    private val getConversationByIdUseCase: GetConversationByIdUseCase,
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
        when (type) {
            QuizType.CONVERSATION -> {
                loadConversations()
                navigateToStep(QuizStep.CONVERSATION_SELECTION)
            }
            QuizType.RANDOM -> {
                generateRandomQuiz()
                navigateToStep(QuizStep.PLAYING)
            }
            QuizType.WORD -> {
                generateRandomQuiz()
                navigateToStep(QuizStep.PLAYING)
            }
            QuizType.SENTENCE -> {
                generateRandomQuiz()
                navigateToStep(QuizStep.PLAYING)
            }
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
                            title = it.selectedScenario ?: "Unknown",
                            type = PlaceTypeItem.Location(it.selectedLocation ?: "Unknown"),
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
        loadChatMessages(conversation.conversationId!!)
    }

    private fun loadChatMessages(conversationId: Long) {
        viewModelScope.launch {
            updateIsLoading(true)
            try {
                val messages = getMessagesByConversationIdUseCase(conversationId)
                val chatMessages = messages.toChatMessageModelList()
                
                _uiState.update { currentState ->
                    currentState.copy(chatMessages = chatMessages.toImmutableList())
                }
                
                generateConversationQuiz(chatMessages)
                navigateToStep(QuizStep.PLAYING)
            } catch (e: Exception) {
                updateErrorMessage("Failed to load chat messages: ${e.localizedMessage}")
            }
            updateIsLoading(false)
        }
    }

    private fun generateConversationQuiz(chatMessages: List<com.malharang.app.presentation.model.ChatMessageModel>) {
        viewModelScope.launch {
            updateQuizLoadingState(QuizContract.QuizLoadingState.LOADING)
            try {
                val messageDataList = chatMessages.map { 
                    com.malharang.app.domain.model.MessageData(
                        role = it.sender.name,
                        content = it.text
                    ) 
                }
                val result = quizUseCase("conversation", messageDataList)
                result.onSuccess { quizList ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            quizQuestions = quizList.toImmutableList(),
                            currentQuestionIndex = 0,
                            selectedAnswerIndex = null,
                            userAnswers = emptyList<Int>().toImmutableList(),
                            score = 0
                        )
                    }
                }.onFailure { 
                    updateErrorMessage("Failed to generate quiz: ${it.localizedMessage}")
                    updateQuizLoadingState(QuizContract.QuizLoadingState.ERROR)
                }
                updateQuizLoadingState(QuizContract.QuizLoadingState.QUESTION)
            } catch (e: Exception) {
                updateErrorMessage("Failed to generate quiz: ${e.localizedMessage}")
                updateQuizLoadingState(QuizContract.QuizLoadingState.ERROR)
            }
        }
    }

    private fun generateRandomQuiz() {
        viewModelScope.launch {
            updateQuizLoadingState(QuizContract.QuizLoadingState.LOADING)
            try {
                val result = quizUseCase("random", emptyList()) // Random quiz
                result.onSuccess { quizList ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            quizQuestions = quizList.toImmutableList(),
                            currentQuestionIndex = 0,
                            selectedAnswerIndex = null,
                            userAnswers = emptyList<Int>().toImmutableList(),
                            score = 0
                        )
                    }
                }.onFailure { 
                    updateErrorMessage("Failed to generate random quiz: ${it.localizedMessage}")
                    updateQuizLoadingState(QuizContract.QuizLoadingState.ERROR)
                }
                updateQuizLoadingState(QuizContract.QuizLoadingState.QUESTION)
            } catch (e: Exception) {
                updateErrorMessage("Failed to generate random quiz: ${e.localizedMessage}")
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
        val selectedAnswer = currentQuestion.options[selectedIndex]
        val isCorrect = selectedAnswer == currentQuestion.answer
        val newScore = if (isCorrect) currentState.score + 1 else currentState.score
        val newAnswers = currentState.userAnswers + selectedIndex
        
        _uiState.update { currentState ->
            currentState.copy(
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
        
        _uiState.update { currentState ->
            currentState.copy(countResult = countResult)
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

    private fun navigateToStep(step: QuizStep) {
        _uiState.update { currentState ->
            currentState.copy(currentStep = step)
        }
        viewModelScope.launch {
            _sideEffect.emit(QuizSideEffect.NavigateToNextStep(step))
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