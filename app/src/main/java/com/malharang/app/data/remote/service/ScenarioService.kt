package com.malharang.app.data.remote.service

import com.malharang.app.data.remote.dto.request.ScenarioRequestDto
import com.malharang.app.data.remote.dto.response.ScenarioResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface ScenarioService {

    @POST("/scenario/generate")
    suspend fun postGenerateScenario(
        @Body request: ScenarioRequestDto
    ): ScenarioResponseDto
}
