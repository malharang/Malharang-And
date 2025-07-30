package com.malharang.app.presentation.screen.quiz.model

data class QuizState(
    val currentStep: QuizStep = QuizStep.START,
    val selectedScenarioId: Int? = null,
    val quizType: QuizType? = null,
    val result: CountResult? = null
)