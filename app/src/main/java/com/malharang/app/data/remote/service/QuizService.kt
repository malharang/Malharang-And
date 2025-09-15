package com.malharang.app.data.remote.service

import com.malharang.app.data.remote.dto.request.QuizRequestDto
import com.malharang.app.data.remote.dto.response.QuizResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface QuizService {
    @POST("/quiz")
    suspend fun postQuiz(
        @Body request: QuizRequestDto
    ): QuizResponseDto
}
