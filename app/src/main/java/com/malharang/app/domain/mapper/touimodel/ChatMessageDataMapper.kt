package com.malharang.app.domain.mapper.touimodel

import com.malharang.app.domain.model.ChatMessageData
import com.malharang.app.presentation.model.ChatMessageModel
import com.malharang.app.presentation.model.SenderType

fun ChatMessageData.toUiModel(): ChatMessageModel {
    val senderType = when (this.role) {
        "assistant" -> SenderType.BOT
        "user" -> SenderType.USER
        else -> SenderType.BOT
    }

    return ChatMessageModel(
        sender = senderType,
        text = this.content,
    )
}

fun List<ChatMessageData>.toUiModelList(): List<ChatMessageModel> {
    return this.map { it.toUiModel() }
}
