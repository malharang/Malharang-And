package com.malharang.app.presentation.screen.quiz

import androidx.compose.runtime.Immutable
import com.malharang.app.domain.model.QuizData
import com.malharang.app.presentation.model.ChatMessageModel
import com.malharang.app.presentation.model.MissionCardModel
import com.malharang.app.presentation.screen.quiz.model.CountResult
import com.malharang.app.presentation.screen.quiz.model.QuizState
import com.malharang.app.presentation.screen.quiz.model.QuizStep
import com.malharang.app.presentation.screen.quiz.model.QuizType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

class QuizContract {

    enum class QuizLoadingState {
        LOADING,
        QUESTION, ERROR,
        RESULT
    }

    @Immutable
    data class QuizUiState(
        val currentStep: QuizStep = QuizStep.START,
        val selectedType: QuizType? = null,
        val conversations: ImmutableList<MissionCardModel> = persistentListOf(),
        val selectedConversation: MissionCardModel? = null,
        val chatMessages: ImmutableList<ChatMessageModel> = persistentListOf(),
        val quizQuestions: ImmutableList<QuizData> = persistentListOf(),
        val quizState: QuizState? = null,
        val quizLoadingState: QuizLoadingState = QuizLoadingState.LOADING,
        val currentQuestionIndex: Int = 0,
        val selectedAnswerIndex: Int? = null,
        val userAnswers: ImmutableList<Int> = persistentListOf(),
        val score: Int = 0,
        val countResult: CountResult? = null,
        val isLoading: Boolean = false,
        val errorMessage: String? = null
    )

    sealed interface QuizSideEffect {
        data class NavigateToNextStep(val step: QuizStep) : QuizSideEffect
        data class NavigateToResult(val score: Int, val total: Int) : QuizSideEffect
        data object NavigateBack : QuizSideEffect
        data object NavigateToMission : QuizSideEffect
        data class ShowToast(val message: String) : QuizSideEffect
        data object QuizCompleted : QuizSideEffect
    }
}
