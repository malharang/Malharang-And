package com.malharang.app.data.remote.datasource

import com.malharang.app.data.remote.dto.response.QuizResponseDto
import com.malharang.app.domain.model.MessageData

interface QuizRemoteDataSource {
    suspend fun postQuiz(
        quizType: String,
        messages: List<MessageData>
    ): QuizResponseDto
}
