package com.malharang.app.domain.repository

import com.malharang.app.domain.model.ScenarioData

interface ScenarioRepository {
    suspend fun postGenerateScenario(
        location: String,
        goal: String?
    ) : Result<List<ScenarioData>>
}