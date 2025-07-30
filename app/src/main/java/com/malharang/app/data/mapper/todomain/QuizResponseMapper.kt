package com.malharang.app.data.mapper.todomain

import com.malharang.app.data.remote.dto.response.QuizItemDto
import com.malharang.app.data.remote.dto.response.QuizResponseDto
import com.malharang.app.domain.model.QuizData

fun QuizItemDto.toDomain(): QuizData = QuizData(
    question = this.question,
    options = this.options,
    answer = this.options.getOrNull(this.answer - 1) ?: "", // 1-based index 안전 처리
    type = this.type
)

fun QuizResponseDto.toDomainList(): List<QuizData> {
    return this.data.map { it.toDomain() }
}