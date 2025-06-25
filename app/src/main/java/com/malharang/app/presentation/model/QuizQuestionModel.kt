package com.malharang.app.presentation.model

data class QuizQuestionModel(
    val question: String,
    val options: List<String>,
    val answer: String
)