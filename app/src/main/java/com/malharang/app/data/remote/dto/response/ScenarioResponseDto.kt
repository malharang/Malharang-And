package com.malharang.app.data.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScenarioResponseDto(
    @SerialName("success")
    val success: Boolean,

    @SerialName("scenarios")
    val scenarios: List<String>
)

