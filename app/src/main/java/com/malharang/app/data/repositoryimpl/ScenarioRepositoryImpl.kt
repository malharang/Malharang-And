package com.malharang.app.data.repositoryimpl

import com.malharang.app.data.mapper.todomain.toDomain
import com.malharang.app.data.remote.datasource.ScenarioRemoteDataSource
import com.malharang.app.data.remote.dto.request.ScenarioRequestDto
import com.malharang.app.domain.model.ScenarioData
import com.malharang.app.domain.repository.ScenarioRepository
import javax.inject.Inject

class ScenarioRepositoryImpl @Inject constructor(
    private val scenarioRemoteDataSource: ScenarioRemoteDataSource,
) : ScenarioRepository {
    override suspend fun postGenerateScenario(location: String, goal: String?): Result<List<ScenarioData>> = runCatching {
        scenarioRemoteDataSource.postGenerateScenario(
            request = ScenarioRequestDto(
                location = location,
                goal = goal,
            )
        ).toDomain()
    }
}