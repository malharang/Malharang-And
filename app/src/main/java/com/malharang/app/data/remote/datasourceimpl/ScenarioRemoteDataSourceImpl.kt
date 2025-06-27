package com.malharang.app.data.remote.datasourceimpl

import com.malharang.app.data.remote.datasource.ScenarioRemoteDataSource
import com.malharang.app.data.remote.dto.request.ScenarioRequestDto
import com.malharang.app.data.remote.dto.response.ScenarioResponseDto
import com.malharang.app.data.remote.service.ScenarioService
import javax.inject.Inject

class ScenarioRemoteDataSourceImpl @Inject constructor(
    private val scenarioService: ScenarioService
) : ScenarioRemoteDataSource {
    override suspend fun postGenerateScenario(request: ScenarioRequestDto): ScenarioResponseDto =
        scenarioService.postGenerateScenario(request = request)
}

