package com.malharang.app.data.repositoryimpl

import com.malharang.app.data.mapper.todto.toDto
import com.malharang.app.data.mapper.todomain.toDomain
import com.malharang.app.data.remote.datasource.EvaluationRemoteDataSource
import com.malharang.app.domain.model.EvaluationRequestData
import com.malharang.app.domain.model.EvaluationResponseData
import com.malharang.app.domain.repository.EvaluationRepository
import javax.inject.Inject

class EvaluationRepositoryImpl @Inject constructor(
    private val evaluationRemoteDataSource: EvaluationRemoteDataSource
) : EvaluationRepository {
    override suspend fun postEvaluation(evaluationRequest: EvaluationRequestData): Result<EvaluationResponseData> = runCatching {
        evaluationRemoteDataSource.postEvaluation(evaluationRequest.toDto()).toDomain()
    }
}
