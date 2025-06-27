package com.malharang.app.data.remote.datasource

import com.malharang.app.data.remote.dto.request.ScenarioRequestDto
import com.malharang.app.data.remote.dto.response.ScenarioResponseDto

interface ScenarioRemoteDataSource {
    suspend fun postGenerateScenario(
        request: ScenarioRequestDto
    ): ScenarioResponseDto
}