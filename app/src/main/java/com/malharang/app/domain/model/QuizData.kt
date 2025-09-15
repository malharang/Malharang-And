package com.malharang.app.domain.model

data class QuizData(
    val question: String,
    val options: List<String>,
    val answerIndex: Int,
    val type: String
)
