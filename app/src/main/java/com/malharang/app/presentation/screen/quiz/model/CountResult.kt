package com.malharang.app.presentation.screen.quiz.model

@kotlinx.serialization.Serializable
data class CountResult(
    val total: Int,
    val correct: Int
)
