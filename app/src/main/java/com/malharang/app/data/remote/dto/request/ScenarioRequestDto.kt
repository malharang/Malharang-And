package com.malharang.app.data.remote.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScenarioRequestDto(
    @SerialName("location")
    val location: String,

    @SerialName("goal")
    val goal: String? = null
)

