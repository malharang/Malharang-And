package com.malharang.app.domain.usecase

import com.malharang.app.domain.model.MessageData
import com.malharang.app.domain.model.QuizData
import com.malharang.app.domain.repository.QuizRepository
import javax.inject.Inject

class QuizUseCase @Inject constructor(
    private val quizRepository: QuizRepository
) {
    suspend operator fun invoke(
        quizType: String,
        messages: List<MessageData>
    ): Result<List<QuizData>> {
        return quizRepository.postQuiz(quizType, messages)
    }
}
