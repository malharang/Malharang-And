package com.malharang.app.domain.repository

import com.malharang.app.domain.model.EvaluationRequestData
import com.malharang.app.domain.model.EvaluationResponseData

interface EvaluationRepository {
    suspend fun postEvaluation(evaluationRequest: EvaluationRequestData): Result<EvaluationResponseData>
}

