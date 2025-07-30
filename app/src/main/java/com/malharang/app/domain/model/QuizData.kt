package com.malharang.app.domain.model

data class QuizData(
    val question: String,
    val options: List<String>,
    val answer: String, // 정답 텍스트
    val type: String
)
