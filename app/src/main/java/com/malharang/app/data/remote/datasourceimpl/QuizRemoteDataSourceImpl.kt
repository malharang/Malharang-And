package com.malharang.app.data.remote.datasourceimpl

import com.malharang.app.data.mapper.todata.toChatMessageDto
import com.malharang.app.data.remote.datasource.QuizRemoteDataSource
import com.malharang.app.data.remote.dto.request.QuizRequestDto
import com.malharang.app.data.remote.dto.response.QuizResponseDto
import com.malharang.app.data.remote.service.QuizService
import com.malharang.app.domain.model.MessageData
import javax.inject.Inject

class QuizRemoteDataSourceImpl @Inject constructor(
    private val quizService: QuizService
) : QuizRemoteDataSource {

    override suspend fun postQuiz(
        quizType: String,
        messages: List<MessageData>
    ): QuizResponseDto {
        val dto = QuizRequestDto(
            quizType = quizType,
            messages = messages.map { it.toChatMessageDto() }
        )
        return quizService.postQuiz(dto)
    }
}
