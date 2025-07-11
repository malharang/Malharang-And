package com.malharang.app.domain.mapper

import com.malharang.app.domain.model.ChatMessageData
import com.malharang.app.domain.model.MessageData
import com.malharang.app.presentation.model.ChatMessageModel
import com.malharang.app.presentation.model.SenderType

fun ChatMessageModel.toMessageData(conversationId: Long): MessageData {
    return MessageData(
        role = when (sender) {
            SenderType.USER -> "user"
            SenderType.BOT -> "assistant"
        },
        content = text,
        conversationId = conversationId
    )
}

fun ChatMessageData.toMessageData(conversationId: Long): MessageData {
    return MessageData(
        conversationId = conversationId,
        role = role,
        content = content
    )
}
