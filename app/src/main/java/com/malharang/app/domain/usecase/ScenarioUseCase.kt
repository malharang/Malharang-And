package com.malharang.app.domain.usecase

import com.malharang.app.domain.model.ScenarioData
import com.malharang.app.domain.repository.ScenarioRepository
import javax.inject.Inject

class ScenarioUseCase @Inject constructor(
    private val scenarioRepository: ScenarioRepository
) {
    suspend operator fun invoke(
        location: String,
        goal: String?
    ): Result<List<ScenarioData>> = scenarioRepository.postGenerateScenario(
        location = location,
        goal = goal
    )
}
