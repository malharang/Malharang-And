package com.malharang.app.domain.repository

import com.malharang.app.domain.model.MessageData
import com.malharang.app.domain.model.QuizData

interface QuizRepository {
    suspend fun postQuiz(
        quizType: String,
        messages: List<MessageData>
    ): Result<List<QuizData>>
}