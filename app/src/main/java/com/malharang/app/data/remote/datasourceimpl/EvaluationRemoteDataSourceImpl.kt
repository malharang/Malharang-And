package com.malharang.app.data.remote.datasourceimpl

import com.malharang.app.data.remote.datasource.EvaluationRemoteDataSource
import com.malharang.app.data.remote.dto.request.EvaluationRequestDto
import com.malharang.app.data.remote.dto.response.EvaluationResponseDto
import com.malharang.app.data.remote.service.EvaluationService
import javax.inject.Inject

class EvaluationRemoteDataSourceImpl @Inject constructor(
    private val evaluationService: EvaluationService
) : EvaluationRemoteDataSource {
    override suspend fun postEvaluation(request: EvaluationRequestDto): EvaluationResponseDto =
        evaluationService.postEvaluation(request = request)
}
