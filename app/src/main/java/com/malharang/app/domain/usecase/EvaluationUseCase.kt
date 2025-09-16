package com.malharang.app.domain.usecase

import com.malharang.app.domain.model.EvaluationRequestData
import com.malharang.app.domain.model.EvaluationResponseData
import com.malharang.app.domain.repository.EvaluationRepository
import javax.inject.Inject

class EvaluationUseCase @Inject constructor(
    private val evaluationRepository: EvaluationRepository
) {
    suspend operator fun invoke(
        evaluationRequest: EvaluationRequestData
    ): Result<EvaluationResponseData> {
        return evaluationRepository.postEvaluation(evaluationRequest)
    }
}
