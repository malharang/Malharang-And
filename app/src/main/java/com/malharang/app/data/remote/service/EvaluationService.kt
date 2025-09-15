package com.malharang.app.data.remote.service

import com.malharang.app.data.remote.dto.request.EvaluationRequestDto
import com.malharang.app.data.remote.dto.response.EvaluationResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface EvaluationService {
    @POST("/evaluation")
    suspend fun postEvaluation(
        @Body request: EvaluationRequestDto
    ): EvaluationResponseDto
}
