package com.malharang.app.data.mapper.todomain

import com.malharang.app.data.remote.dto.response.ScenarioResponseDto
import com.malharang.app.domain.model.ScenarioData

fun ScenarioResponseDto.toDomain(): List<ScenarioData> =
    scenarios.map { ScenarioData(title = it) }
