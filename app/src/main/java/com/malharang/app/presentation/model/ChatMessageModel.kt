package com.malharang.app.presentation.model

import com.malharang.app.domain.model.EvaluationResponseData
import com.malharang.app.presentation.screen.chat.type.EvaluationState

data class ChatMessageModel(
    val text: String,
    val sender: SenderType,
    val translatedText: String? = null,
    val isTranslating: Boolean = false,
    val isSoundPlaying: Boolean = false,
    val isTranslationVisible: Boolean = false,
    val evaluationState: EvaluationState? = null,
    val evaluationData: EvaluationResponseData? = null
)

enum class SenderType {
    BOT,
    USER
}
