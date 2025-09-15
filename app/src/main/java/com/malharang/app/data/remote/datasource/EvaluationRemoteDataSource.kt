package com.malharang.app.data.remote.datasource

import com.malharang.app.data.remote.dto.request.EvaluationRequestDto
import com.malharang.app.data.remote.dto.response.EvaluationResponseDto

interface EvaluationRemoteDataSource {
    suspend fun postEvaluation(
        request: EvaluationRequestDto
    ): EvaluationResponseDto
}
