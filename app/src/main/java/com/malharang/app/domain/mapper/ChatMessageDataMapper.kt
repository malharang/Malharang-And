package com.malharang.app.domain.mapper

import com.malharang.app.domain.model.ChatMessageData
import com.malharang.app.domain.model.MessageData
import com.malharang.app.presentation.model.ChatMessageModel
import com.malharang.app.presentation.model.SenderType
import com.malharang.app.presentation.screen.chat.type.EvaluationState

fun List<MessageData>.toChatMessageModelList(): List<ChatMessageModel> {
    return this.mapNotNull { msg ->
        val sender = when (msg.role) {
            "user" -> SenderType.USER
            "assistant" -> SenderType.BOT
            else -> null
        }

        sender?.let {
            // 평가 데이터가 있는 경우 EvaluationState와 EvaluationResponseData 생성
            val evaluationState = if (msg.role == "user" && msg.contextualityPassed != null) {
                if (msg.contextualityPassed == true) EvaluationState.PASS else EvaluationState.NOT_PASS
            } else {
                null
            }

            val evaluationData = if (msg.role == "user" && msg.contextualityPassed != null && msg.grammarPassed != null) {
                try {
                    val grammarErrors = msg.grammarErrors?.let { json ->
                        kotlinx.serialization.json.Json.decodeFromString<List<com.malharang.app.domain.model.GrammarErrorData>>(json)
                    } ?: emptyList()

                    com.malharang.app.domain.model.EvaluationResponseData(
                        data = com.malharang.app.domain.model.EvaluationResultData(
                            contextuality = com.malharang.app.domain.model.ContextualityData(
                                comment = msg.contextualityComment ?: "",
                                contextuality = emptyList(),
                                pass = msg.contextualityPassed ?: false
                            ),
                            grammar = com.malharang.app.domain.model.GrammarData(
                                comment = msg.grammarComment ?: "",
                                grammar = grammarErrors,
                                pass = msg.grammarPassed ?: false
                            )
                        ),
                        message = "",
                        success = true
                    )
                } catch (e: Exception) {
                    null
                }
            } else {
                null
            }

            ChatMessageModel(
                text = msg.content,
                sender = it,
                evaluationState = evaluationState,
                evaluationData = evaluationData
            )
        }
    }
}

fun List<MessageData>.toChatMessageDataList(): List<ChatMessageData> {
    return this.map {
        ChatMessageData(
            role = it.role,
            content = it.content
        )
    }
}
