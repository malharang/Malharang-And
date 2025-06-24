package com.malharang.app.data.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TranslateResponseDTO(
    @SerialName("data")
    val data: TranslationDataDTO
)

@Serializable
data class TranslationDataDTO(
    @SerialName("translations")
    val translations: List<TranslationItemDTO>
)

@Serializable
data class TranslationItemDTO(
    @SerialName("translatedText")
    val translatedText: String
)
