package com.malharang.app.data.remote.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TTSRequestDto(
    @SerialName("text")
    val text: String,

    @SerialName("model_id")
    val modelId: String = "eleven_multilingual_v2", // 기본값 설정

    @SerialName("voice_settings")
    val voiceSettings: VoiceSettings = VoiceSettings()
)

@Serializable
data class VoiceSettings(
    @SerialName("speed")
    val speed: Float = 0.8f
)
