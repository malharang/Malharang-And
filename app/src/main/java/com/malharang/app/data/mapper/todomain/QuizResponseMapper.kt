package com.malharang.app.data.mapper.todomain

import com.malharang.app.data.remote.dto.response.QuizItemDto
import com.malharang.app.data.remote.dto.response.QuizResponseDto
import com.malharang.app.domain.model.QuizData

fun QuizItemDto.toDomain(): QuizData {
    val correctAnswerIndex = this.answer - 1

    return QuizData(
        question = this.question.trim(),
        options = this.options.map { it.trim() },
        answerIndex = correctAnswerIndex,
        type = this.type
    )
}

fun QuizResponseDto.toDomainList(): List<QuizData> {
    return this.data.map { it.toDomain() }
}